package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderLineException;
import kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException;
import kitchenpos.menus.tobe.domain.doubles.MemoryMenuRepository;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderLineException.*;
import static kitchenpos.eatinorders.tobe.domain.exception.IllegalOrderTableStatusException.ALREADY_OCCUPIED;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderTest {

    private final MemoryMenuRepository menuRepository = new MemoryMenuRepository();
    private final OrderPolicy orderPolicy = new OrderPolicy(menuRepository);

    @DisplayName("주문 테이블이 점유 상태이면 주문 생성에 실패한다.")
    @Test
    void create_Exception() {
        OrderTable orderTable = new OrderTable();
        orderTable.occupy();

        assertThatThrownBy(() -> new EatInOrder(orderPolicy, orderTable))
                .isInstanceOf(IllegalOrderTableStatusException.class)
                .hasMessage(ALREADY_OCCUPIED);
    }

    @DisplayName("주문 메뉴가 존재하지 않으면 주문할 수 없다.")
    @Test
    void create_Exception2() {
        OrderTable orderTable = new OrderTable();
        OrderLineItem oli = new OrderLineItem(UUID.randomUUID(), 1L, 10_000L);

        assertThatThrownBy(() -> new EatInOrder(orderPolicy, orderTable, oli))
                .isInstanceOf(IllegalOrderLineException.class)
                .hasMessage(NO_MENU);
    }

    @DisplayName("주문 메뉴가 진열 상태가 아니면 주문할 수 없다.")
    @Test
    void create_Exception3() {
        // given
        Menu menu = createMenu(10_000L, false);
        menuRepository.save(menu);

        OrderTable orderTable = new OrderTable();
        OrderLineItem oli = new OrderLineItem(menu.getId(), 1L, 10_000L);

        // expected
        assertThatThrownBy(() -> new EatInOrder(orderPolicy, orderTable, oli))
                .isInstanceOf(IllegalOrderLineException.class)
                .hasMessage(NOT_DISPLAYED);
    }

    @DisplayName("주문 메뉴와 메뉴의 가격이 일치하지 않으면 주문할 수 없다.")
    @Test
    void create_Exception4() {
        // given
        Menu menu = createMenu(70_000L, true);
        menuRepository.save(menu);

        OrderTable orderTable = new OrderTable();
        OrderLineItem oli = new OrderLineItem(menu.getId(), 1L, 10_000L);

        // expected
        assertThatThrownBy(() -> new EatInOrder(orderPolicy, orderTable, oli))
                .isInstanceOf(IllegalOrderLineException.class)
                .hasMessage(PRICE_MISMATCH);
    }

    @DisplayName("주문 생성 성공 케이스")
    @Test
    void create() {
        // given
        Menu menu = createMenu(10_000L, true);
        menuRepository.save(menu);

        OrderTable orderTable = new OrderTable();
        OrderLineItem oli = new OrderLineItem(menu.getId(), 1L, 10_000L);

        // expected
        assertThatNoException()
                .isThrownBy(() -> new EatInOrder(orderPolicy, orderTable, oli));
    }

    private Menu createMenu(long price, boolean displayed) {
        MenuProduct menuProduct = new MenuProduct(null, 1, price);
        return new Menu( null, null, price, null, displayed, menuProduct);
    }
}
