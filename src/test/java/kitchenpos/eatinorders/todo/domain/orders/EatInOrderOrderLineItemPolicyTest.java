package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.support.domain.MenuClient;
import kitchenpos.support.domain.OrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DisplayName("주문아이템 정책")
@ExtendWith(MockitoExtension.class)
class EatInOrderOrderLineItemPolicyTest {
    @Mock
    private MenuClient menuClient;

    @InjectMocks
    private EatInOrderOrderLineItemPolicy orderLineItemPolicy;

    @DisplayName("[성공] 주문아이템의 유효한 메뉴인지 확인한다.")
    @Test
    void checkMenu() {
        Menu menu = menu();
        OrderLineItem orderLineItem = new OrderLineItem(menu.getId(), BigDecimal.valueOf(19_000), 1L);
        given(menuClient.getMenu(orderLineItem.getMenuId())).willReturn(menu);

        assertThatNoException().isThrownBy(() -> orderLineItemPolicy.checkMenu(orderLineItem));
    }

    @DisplayName("[실패] 주문아이템의 메뉴가 숨겨진 상태이면 예외가 발생한다.")
    @Test
    void fail_checkMenu() {
        Menu menu = menu(19_000, false, menuProduct());
        OrderLineItem orderLineItem = new OrderLineItem(menu.getId(), BigDecimal.valueOf(19_000), 1L);
        given(menuClient.getMenu(orderLineItem.getMenuId())).willReturn(menu);

        assertThatThrownBy(() -> orderLineItemPolicy.checkMenu(orderLineItem))
                .isInstanceOf(KitchenPosIllegalStateException.class);
    }

    @DisplayName("[실패] 요청한 메뉴의 가격이 원래 메뉴의 가격과 다르면 예외가 발생한다.")
    @Test
    void fail2_checkMenu() {
        Menu menu = menu(20_000, true, menuProduct());
        OrderLineItem orderLineItem = new OrderLineItem(menu.getId(), BigDecimal.valueOf(19_000), 1L);
        given(menuClient.getMenu(orderLineItem.getMenuId())).willReturn(menu);

        assertThatThrownBy(() -> orderLineItemPolicy.checkMenu(orderLineItem))
                .isInstanceOf(KitchenPosIllegalArgumentException.class);
    }
}
