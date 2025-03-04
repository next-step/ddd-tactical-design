package kitchenpos.eatinorders.application.tobe;

import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.infra.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.presentation.dto.*;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.common.OrderType;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusException;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderServiceTest {

    private static final MenuId CHICKEN = MenuId.generate();
    private static final MenuId COKE = MenuId.generate();

    private MenuRepository menuRepository;
    private OrderRepository orderRepository;
    private OrderTableRepository orderTableRepository;

    private OrderLineItemsValidator orderLineItemsValidator;

    private EatInOrderService eatInOrderService;

    @BeforeEach
    void setUp() {
        this.menuRepository = new InMemoryMenuRepository();
        this.orderRepository = new InMemoryOrderRepository();
        this.orderTableRepository = new InMemoryOrderTableRepository();
        this.orderLineItemsValidator = new OrderLineItemsValidator(menuRepository);

        this.eatInOrderService = new EatInOrderService(orderRepository, orderTableRepository, orderLineItemsValidator);

        menuRepository.save(createMenu(CHICKEN, "후라이드치킨", 25_000, true));
        menuRepository.save(createMenu(COKE, "콜라", 3_000, true));
    }

    @DisplayName("매장 내 식사 주문을 생성한다")
    @Test
    void create() {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, CHICKEN, 1, new Price(25_000))
        );
        EatInOrderCreateRequest request = new EatInOrderCreateRequest(orderLineItems, table.getId());

        EatInOrderCreateResponse response = eatInOrderService.create(request);

        assertAll(
                () -> assertThat(response.getOrderType()).isEqualTo(OrderType.EAT_IN),
                () -> assertThat(response.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(response.getOrderTableId()).isEqualTo(table.getId())
        );
    }

    @DisplayName("대기 중인 주문이 아니면 주문 수락 시 예외 발생한다")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateAccept(EatInOrderStatus status) {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, CHICKEN, 1, new Price(25_000))
        );
        OrderEntity order = orderRepository.save(new OrderEntity(
                OrderType.EAT_IN,
                status.get(),
                orderLineItems,
                null,
                table.getId()
        ));

        assertThatThrownBy(() -> eatInOrderService.accept(order.id()))
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("대기 중인 주문을 수락한다")
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void accept(EatInOrderStatus status) {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, CHICKEN, 1, new Price(25_000))
        );
        OrderEntity order = orderRepository.save(new OrderEntity(
                OrderType.EAT_IN,
                status.get(),
                orderLineItems,
                null,
                table.getId()
        ));

        EatInOrderAcceptResponse result = eatInOrderService.accept(order.id());

        assertThat(result.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("접수된 주문이 아니면 주문 서빙 시 예외 발생한다")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateServe(EatInOrderStatus status) {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, CHICKEN, 1, new Price(25_000))
        );
        OrderEntity order = orderRepository.save(new OrderEntity(
                OrderType.EAT_IN,
                status.get(),
                orderLineItems,
                null,
                table.getId()
        ));

        assertThatThrownBy(() -> eatInOrderService.serve(order.id()))
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("접수한 주문을 서빙한다")
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void serve(EatInOrderStatus status) {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, CHICKEN, 1, new Price(25_000))
        );
        OrderEntity order = orderRepository.save(new OrderEntity(
                OrderType.EAT_IN,
                status.get(),
                orderLineItems,
                null,
                table.getId()
        ));

        EatInOrderServedResponse result = eatInOrderService.serve(order.id());

        assertThat(result.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("서빙된 주문이 아니면 주문 완료 시 예외 발생한다")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void validateCompleted(EatInOrderStatus status) {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, CHICKEN, 1, new Price(25_000))
        );
        OrderEntity order = orderRepository.save(new OrderEntity(
                OrderType.EAT_IN,
                status.get(),
                orderLineItems,
                null,
                table.getId()
        ));

        assertThatThrownBy(() -> eatInOrderService.complete(order.id()))
                .isInstanceOf(InvalidOrderStatusException.class);
    }

    @DisplayName("주문을 완료 처리 한다")
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void complete(EatInOrderStatus status) {
        OrderTable table = orderTableRepository.save(createOrderTable("1번테이블", 4, true));
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, CHICKEN, 1, new Price(25_000))
        );
        OrderEntity order = orderRepository.save(new OrderEntity(
                OrderType.EAT_IN,
                status.get(),
                orderLineItems,
                null,
                table.getId()
        ));

        EatInOrderCompletedResponse result = eatInOrderService.complete(order.id());

        assertThat(result.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
    }

    private Menu createMenu(MenuId menuId, String name, int price, boolean displayed) {
        return new Menu(
                menuId,
                new MenuName(name, (menuName) -> false),
                new Price(price),
                MenuGroupId.generate(),
                new MenuProducts(
                        new MenuProduct(ProductId.generate(), 1, price)
                ),
                displayed
        );
    }

    private OrderTable createOrderTable(String name, int numberOfGuests, boolean occupied) {
        return new OrderTable(
                OrderTableId.generate(),
                new OrderTableName(name),
                new PositiveNumber(numberOfGuests),
                occupied
        );
    }

}
