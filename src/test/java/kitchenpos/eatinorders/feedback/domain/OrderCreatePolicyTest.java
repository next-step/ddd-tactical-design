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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderCreatePolicyTest {
    private OrderTableRepository orderTableRepository;
    private MenuRepository menuRepository;
    private OrderCreatePolicy orderCreatePolicy;

    private Menu 후라이드_두마리_메뉴;
    private Menu 양념_두마리_메뉴;
    private OrderTable 일번테이블;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        menuRepository = new InMemoryMenuRepository();
        orderCreatePolicy = new OrderCreatePolicy(orderTableRepository, menuRepository);

        후라이드_두마리_메뉴 = new Menu(1L, "후라이드 두마리", 20000, 1L, List.of(new MenuProduct(1L, 2)));
        양념_두마리_메뉴 = new Menu(2L, "양념 두마리", 22000, 1L, List.of(new MenuProduct(2L, 2)));
        menuRepository.save(후라이드_두마리_메뉴);
        menuRepository.save(양념_두마리_메뉴);

        일번테이블 = new OrderTable(1L, "1번 테이블");
        orderTableRepository.save(일번테이블);
    }

    @DisplayName("주문을 생성한다.")
    @Test
    void create() {
        일번테이블.use();
        List<OrderLineItem> orderLineItems = List.of(
                new OrderLineItem(1L, BigDecimal.valueOf(20000), 2),
                new OrderLineItem(2L, BigDecimal.valueOf(22000), 2)
        );

        assertDoesNotThrow(() -> orderCreatePolicy.validate(orderLineItems, 일번테이블.getId()));
    }

    @DisplayName("주문의 가격과 메뉴의 가격이 다르면 예외가 발생한다.")
    @Test
    void createWithWrongPrice() {
        일번테이블.use();

        List<OrderLineItem> orderLineItems = List.of(
                new OrderLineItem(1L, BigDecimal.valueOf(20000), 2),
                new OrderLineItem(2L, BigDecimal.valueOf(50000), 2)
        );

        assertThatThrownBy(() -> orderCreatePolicy.validate(orderLineItems, 일번테이블.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블이 사용되지 않은 상태일 때 매장 주문을 하면 예외가 발생한다.")
    @Test
    void createWithNotOccupiedTable() {
        List<OrderLineItem> orderLineItems = List.of(
                new OrderLineItem(1L, BigDecimal.valueOf(20000), 2),
                new OrderLineItem(2L, BigDecimal.valueOf(22000), 2)
        );

        assertThatThrownBy(() -> orderCreatePolicy.validate(orderLineItems, 일번테이블.getId()))
                .isInstanceOf(IllegalStateException.class);

    }
}
