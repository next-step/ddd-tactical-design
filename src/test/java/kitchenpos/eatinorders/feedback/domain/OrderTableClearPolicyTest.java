package kitchenpos.eatinorders.feedback.domain;

import kitchenpos.eatinorders.feedback.InMemoryEatInOrderRepository;
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

class OrderTableClearPolicyTest {
    private EatInOrderRepository eatInOrderRepository = new InMemoryEatInOrderRepository();
    private OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();
    private MenuRepository menuRepository = new InMemoryMenuRepository();

    private OrderTableClearPolicy orderTableClearPolicy;
    private OrderCreatePolicy orderCreatePolicy;

    private OrderTable orderTable;
    private EatInOrder eatInOrder;
    private Menu menu;

    @BeforeEach
    void setUp() {
        orderTableClearPolicy = new OrderTableClearPolicy(eatInOrderRepository);
        orderCreatePolicy = new OrderCreatePolicy(orderTableRepository, menuRepository);

        orderTable = new OrderTable(1L, "1번 테이블");
        orderTableRepository.save(orderTable);
        orderTable.use();

        menu = new Menu(1L, "후라이드 치킨", 20000, 1L, List.of(new MenuProduct(1L, 1)));
        menuRepository.save(menu);

        eatInOrder = new EatInOrder(List.of(new OrderLineItem(1L, BigDecimal.valueOf(20000), 2)), 1L, orderCreatePolicy);
        eatInOrderRepository.save(eatInOrder);
    }

    @DisplayName("주문 테이블을 치운다.")
    @Test
    void clear() {
        eatInOrder.accept();
        eatInOrder.serve();
        eatInOrder.complete();

        assertThat(orderTableClearPolicy.canClear(orderTable.getId())).isTrue();
    }

    @DisplayName("완료되지 않은 주문이 있는 테이블을 치울 수 없다.")
    @Test
    void clearOrderTableWithNotCompleteOrder() {
        assertThat(orderTableClearPolicy.canClear(orderTable.getId())).isFalse();
    }
}
