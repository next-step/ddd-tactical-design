package kitchenpos.eatinorders.feedback.domain;

import kitchenpos.eatinorders.feedback.InMemoryOrderTableRepository;
import kitchenpos.menus.tobe.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderTest {
    private OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();
    private MenuRepository menuRepository = new InMemoryMenuRepository();
    private OrderCreatePolicy orderCreatePolicy = new OrderCreatePolicy(orderTableRepository, menuRepository);

    private List<OrderLineItem> orderLineItems;
    private OrderTable orderTable;
    private Menu menu;

    @BeforeEach
    void setUp() {
        orderLineItems = List.of(new OrderLineItem(1L, BigDecimal.valueOf(20000), 2));

        orderTable = new OrderTable(1L, "1번 테이블");
        orderTableRepository.save(orderTable);
        orderTable.use();

        menu = new Menu(1L, "후라이드 치킨", 20000, 1L, List.of(new MenuProduct(1L, 1)));
        menuRepository.save(menu);
    }

    @DisplayName("매장 주문을 생성한다.")
    @Test
    void create() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L, orderCreatePolicy);

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @DisplayName("매장 주문을 접수한다.")
    @Test
    void accept() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L, orderCreatePolicy);

        eatInOrder.accept();

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("WAITING 상태가 아닌 매장 주문은 접수할 수 없다.")
    @Test
    void acceptFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L, orderCreatePolicy);
        eatInOrder.accept();

        assertThatThrownBy(eatInOrder::accept)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문을 서빙한다.")
    @Test
    void serve() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L, orderCreatePolicy);
        eatInOrder.accept();

        eatInOrder.serve();

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("ACCEPT 상태가 아닌 매장 주문은 서빙할 수 없다.")
    @Test
    void serveFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L, orderCreatePolicy);

        assertThatThrownBy(eatInOrder::serve)
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문을 완료한다.")
    @Test
    void complete() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L, orderCreatePolicy);
        eatInOrder.accept();
        eatInOrder.serve();

        eatInOrder.complete();

        assertThat(eatInOrder.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("SERVED 상태가 아닌 매장 주문은 완료할 수 없다.")
    @Test
    void completeFail() {
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, 1L, orderCreatePolicy);

        assertThatThrownBy(eatInOrder::complete)
                .isInstanceOf(IllegalStateException.class);
    }
}
