package kitchenpos.support.domain;

import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.infra.MenuClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문아이템")
class OrderLineItemTest {
    private MenuRepository menuRepository;
    private MenuClient menuClient;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuClient = new MenuClientImpl(menuRepository);
    }


    @DisplayName("[성공] 주문아이템을 생성한다.")
    @Test
    void create() {
        UUID menuId = menu().getId();
        BigDecimal price = BigDecimal.valueOf(34_000);
        long quantity = 1L;

        OrderLineItem orderLineItem = new OrderLineItem(menuId, price, quantity);

        assertAll(
                () -> assertThat(orderLineItem.getMenuId()).isEqualTo(menuId),
                () -> assertThat(orderLineItem.getPrice()).isEqualTo(BigDecimal.valueOf(34_000)),
                () -> assertThat(orderLineItem.getQuantity()).isEqualTo(1L)
        );
    }

    @DisplayName("[실패] 메뉴가 숨긴상태인 주문 아이템은 주문을 생성할 수 없다.")
    @Test
    void fail_create() {
        UUID menuId = setUpMenu(19_000, false).getId();
        OrderLineItem orderLineItem = new OrderLineItem(menuId, BigDecimal.valueOf(19_000), 1L);

        assertThatThrownBy(() -> new OrderLineItem(orderLineItem, menuClient))
                .isInstanceOf(KitchenPosIllegalStateException.class);
    }

    private Menu setUpMenu(long price, boolean displayed) {
        return menuRepository.save(menu(price, displayed, menuProduct()));
    }
}
