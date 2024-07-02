package kitchenpos.orders.common.domain.tobe;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.domain.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("OrderLineItem")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderLineItemTest {

    @Test
    void 노출되어있지않은_메뉴로_주문라인아이템을_생성하면_예외를_던진다() {
        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
                ProductFixture.createFired());
        menu.hide();

        assertThatThrownBy(() -> createOrderLineItem(menu))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 주문라인아이템을_생성한다() {
        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
                ProductFixture.createFired());

        assertThatNoException().isThrownBy(() -> createOrderLineItem(menu));
    }

    private static void createOrderLineItem(Menu menu) {
        new OrderLineItem(menu, new MenuQuantity(OrderType.TAKEOUT, 2));
    }
}