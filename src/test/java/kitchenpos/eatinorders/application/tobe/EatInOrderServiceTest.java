package kitchenpos.eatinorders.application.tobe;

import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.infra.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.common.OrderType;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateResponse;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
                () -> assertThat(response.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(response.getOrderTableId()).isEqualTo(table.getId())
        );
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
