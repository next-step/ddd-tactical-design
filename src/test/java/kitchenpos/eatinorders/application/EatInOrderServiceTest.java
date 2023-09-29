package kitchenpos.eatinorders.application;

import kitchenpos.deliveryorders.domain.KitchenridersClient;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.shared.dto.EatInOrderLineItemDto;
import kitchenpos.eatinorders.shared.dto.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.shared.dto.EatInOrderDto;
import kitchenpos.eatinorders.tobe.domain.order.EatInMenuClient;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.displayedMenu;
import static kitchenpos.Fixtures.eatInOrderLineItem;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.occupiedOrderTable;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeliveryOrderServiceTest {
    private EatInOrderRepository eatInOrderRepository;
    private EatInMenuClient menuClient;

    private MenuRepository menuRepository;
    private ProductRepository productRepository;

    private EatInOrderService eatInOrderService;
    private KitchenridersClient kitchenridersClient;
    private OrderTableRepository orderTableRepository;

    Menu displayedMenu;
    Menu hidedMenu;
    Product product;
    OrderTable orderTable;


    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        productRepository = new InMemoryProductRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        kitchenridersClient = new FakeKitchenridersClient();
        product = productRepository.save(product());
        orderTable = orderTableRepository.save(occupiedOrderTable());
        menuClient = new InMemoryEatInMenuClient(menuRepository);
        eatInOrderService = new EatInOrderService(eatInOrderRepository, menuRepository, orderTableRepository, menuClient);
        displayedMenu = menuRepository.save(displayedMenu(1L, menuProduct(product, 1L)));
        hidedMenu = menuRepository.save(menu(1L, menuProduct(product, 1L)));
    }

    @DisplayName("매장 주문을 성공한다.")
    @Test
    void success_create_eat_in_order() {
        //given
        EatInOrderCreateRequest request = createEatInOrderRequest(List.of(createDisplayedEatInOrderLineItemDto(displayedMenu.getPrice())), orderTable.getId());

        //when
        EatInOrderDto result = eatInOrderService.create(request);

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.WAITING);
        assertThat(result.getType()).isEqualTo(OrderType.EAT_IN);
        assertThat(result.getOrderDateTime()).isNotNull();
        assertThat(result.getOrderTable()).isNotNull();
    }

    @DisplayName("매장 주문을 성공하려면 메뉴가 노출상태여야 한다.")
    @Test
    void menu_of_eat_in_order_should_be_isDisplayed() {
        //given
        EatInOrderCreateRequest request = createEatInOrderRequest(List.of(createHidedEatInOrderLineItemDto(displayedMenu.getPrice())), orderTable.getId());

        //when
        assertThatThrownBy(() -> eatInOrderService.create(request))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문 메뉴 가격은 주문항목 가격과 같아야 한다.")
    @Test
    void menu_price_of_eat_in_order_and_order_line_item_price_should_be_same() {
        //given
        EatInOrderCreateRequest request = createEatInOrderRequest(List.of(createDisplayedEatInOrderLineItemDto(BigDecimal.TEN)), orderTable.getId());

        //when
        assertThatThrownBy(() -> eatInOrderService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
    @Test
    void only_waiting_order_can_be_accept() {

        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.COMPLETED, LocalDateTime.now(), new EatInOrderLineItems(Arrays.asList(eatInOrderLineItem(displayedMenu, menuClient))), orderTable);

        eatInOrderRepository.save(eatInOrder);

        assertThatThrownBy(() -> eatInOrderService.accept(eatInOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("접수중인 주문만 서빙할 수 있다.")
    @Test
    void only_accepted_order_can_be_serve() {

        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.COMPLETED, LocalDateTime.now(), new EatInOrderLineItems(Arrays.asList(eatInOrderLineItem(displayedMenu, menuClient))), orderTable);

        eatInOrderRepository.save(eatInOrder);

        assertThatThrownBy(() -> eatInOrderService.serve(eatInOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("배달한 주문만 배달 완료할 수 있다.")
    @Test
    void only_delivery_completed_order_can_be_complete() {

        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.COMPLETED, LocalDateTime.now(), new EatInOrderLineItems(Arrays.asList(eatInOrderLineItem(displayedMenu, menuClient))), orderTable);

        eatInOrderRepository.save(eatInOrder);

        assertThatThrownBy(() -> eatInOrderService.complete(eatInOrder.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    private EatInOrderCreateRequest createEatInOrderRequest(List<EatInOrderLineItemDto> eatInOrderLineItemDtos, UUID orderTableId) {
        return new EatInOrderCreateRequest(eatInOrderLineItemDtos, orderTableId);
    }

    private EatInOrderLineItemDto createDisplayedEatInOrderLineItemDto(BigDecimal price) {
        return new EatInOrderLineItemDto(displayedMenu.getId(), 2, price);
    }

    private EatInOrderLineItemDto createHidedEatInOrderLineItemDto(BigDecimal price) {
        return new EatInOrderLineItemDto(hidedMenu.getId(), 2, price);
    }
}