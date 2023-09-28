package kitchenpos.deliveryorders.application;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderAddress;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItems;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;
import kitchenpos.deliveryorders.domain.KitchenridersClient;
import kitchenpos.deliveryorders.domain.MenuClient;
import kitchenpos.deliveryorders.shared.dto.DeliveryOrderDto;
import kitchenpos.deliveryorders.shared.dto.DeliveryOrderLineItemDto;
import kitchenpos.deliveryorders.shared.dto.request.DeliveryOrderCreateRequest;
import kitchenpos.eatinorders.application.FakeKitchenridersClient;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.deliveryOrderLineItem;
import static kitchenpos.Fixtures.displayedMenu;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeliveryOrderServiceTest {
    private DeliveryOrderRepository deliveryOrderRepository;
    private MenuClient menuClient;

    private MenuRepository menuRepository;
    private ProductRepository productRepository;

    private DeliveryOrderService deliveryOrderService;
    private KitchenridersClient kitchenridersClient;

    Menu displayedMenu;
    Menu hidedMenu;
    Product product;


    @BeforeEach
    void setUp() {
        deliveryOrderRepository = new InMemoryDeliveryOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        product = productRepository.save(product());
        menuClient = new InMemoryMenuClient(menuRepository);
        deliveryOrderService = new DeliveryOrderService(deliveryOrderRepository, menuClient, kitchenridersClient);
        displayedMenu = menuRepository.save(displayedMenu(1L, menuProduct(product, 1L)));
        hidedMenu = menuRepository.save(menu(1L, menuProduct(product, 1L)));
    }

    @DisplayName("배달 주문을 성공한다.")
    @Test
    void success_create_delivery_order() {
        //given
        DeliveryOrderCreateRequest request = createDeliveryOrderRequest(List.of(createDisplayedDeliveryOrderLineItemDto(displayedMenu.getPrice())), "서울");

        //when
        DeliveryOrderDto result = deliveryOrderService.create(request);

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.WAITING);
        assertThat(result.getType()).isEqualTo(OrderType.DELIVERY);
        assertThat(result.getOrderDateTime()).isNotNull();
        assertThat(result.getDeliveryAddress()).isEqualTo(request.getDeliveryAddress());
    }

    @DisplayName("배달 주문을 성공하려면 메뉴가 노출상태여야 한다.")
    @Test
    void menu_of_delivery_order_should_be_isDisplayed() {
        //given
        DeliveryOrderCreateRequest request = createDeliveryOrderRequest(List.of(createHidedDeliveryOrderLineItemDto(displayedMenu.getPrice())), "서울");

        //when
        assertThatThrownBy(() -> deliveryOrderService.create(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달 주문은 주소가 필수값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    void delivery_order_should_contain_delivery_address(String deliveryAddress) {
        //given
        DeliveryOrderCreateRequest request = createDeliveryOrderRequest(List.of(createDisplayedDeliveryOrderLineItemDto(displayedMenu.getPrice())), deliveryAddress);

        //when
        assertThatThrownBy(() -> deliveryOrderService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주문은 주소가 필수값이다.")
    @Test
    void menu_price_of_delivery_order_and_order_line_item_price_should_be_same() {
        //given
        DeliveryOrderCreateRequest request = createDeliveryOrderRequest(List.of(createDisplayedDeliveryOrderLineItemDto(BigDecimal.TEN)), "서울");

        //when
        assertThatThrownBy(() -> deliveryOrderService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @Test
    void only_waiting_order_can_be_accept() {

        DeliveryOrder deliveryOrder = new DeliveryOrder(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.DELIVERING, LocalDateTime.now(), new DeliveryOrderLineItems(List.of(deliveryOrderLineItem(displayedMenu, menuClient))), new DeliveryOrderAddress("서울"));
        deliveryOrderRepository.save(deliveryOrder);

        assertThatThrownBy(() -> deliveryOrderService.accept(deliveryOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("접수중인 주문만 서빙할 수 있다.")
    @Test
    void only_accepted_order_can_be_serve() {

        DeliveryOrder deliveryOrder = new DeliveryOrder(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.DELIVERING, LocalDateTime.now(), new DeliveryOrderLineItems(List.of(deliveryOrderLineItem(displayedMenu, menuClient))), new DeliveryOrderAddress("서울"));
        deliveryOrderRepository.save(deliveryOrder);

        assertThatThrownBy(() -> deliveryOrderService.serve(deliveryOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("서빙중인 주문만 배달할 수 있다.")
    @Test
    void only_served_order_can_be_delivery_start() {

        DeliveryOrder deliveryOrder = new DeliveryOrder(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.DELIVERING, LocalDateTime.now(), new DeliveryOrderLineItems(List.of(deliveryOrderLineItem(displayedMenu, menuClient))), new DeliveryOrderAddress("서울"));
        deliveryOrderRepository.save(deliveryOrder);

        assertThatThrownBy(() -> deliveryOrderService.startDelivery(deliveryOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달한 주문만 배달 완료할 수 있다.")
    @Test
    void only_delivery_started_order_can_be_complete_delivery() {

        DeliveryOrder deliveryOrder = new DeliveryOrder(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.SERVED, LocalDateTime.now(), new DeliveryOrderLineItems(List.of(deliveryOrderLineItem(displayedMenu, menuClient))), new DeliveryOrderAddress("서울"));
        deliveryOrderRepository.save(deliveryOrder);

        assertThatThrownBy(() -> deliveryOrderService.completeDelivery(deliveryOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달한 주문만 배달 완료할 수 있다.")
    @Test
    void only_delivery_completed_order_can_be_complete() {

        DeliveryOrder deliveryOrder = new DeliveryOrder(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.SERVED, LocalDateTime.now(), new DeliveryOrderLineItems(List.of(deliveryOrderLineItem(displayedMenu, menuClient))), new DeliveryOrderAddress("서울"));
        deliveryOrderRepository.save(deliveryOrder);

        assertThatThrownBy(() -> deliveryOrderService.complete(deliveryOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    private DeliveryOrderCreateRequest createDeliveryOrderRequest(List<DeliveryOrderLineItemDto> deliveryOrderLineItemDtos, String deliveryAddress) {
        return new DeliveryOrderCreateRequest(deliveryOrderLineItemDtos, deliveryAddress);
    }

    private DeliveryOrderLineItemDto createDisplayedDeliveryOrderLineItemDto(BigDecimal price) {
        return new DeliveryOrderLineItemDto(displayedMenu.getId(), 2, price);
    }

    private DeliveryOrderLineItemDto createHidedDeliveryOrderLineItemDto(BigDecimal price) {
        return new DeliveryOrderLineItemDto(hidedMenu.getId(), 2, price);
    }
}