package kitchenpos.orders.common.domain.tobe;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.fixture.StoreOrderFixture;
import kitchenpos.menus.domain.tobe.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("OrderLineItems")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderLineItemsTest {

    @Test
    void 주문아이템들이_null일_경우_예외를_던진다() {
        assertThatThrownBy(
            () -> new OrderLineItems(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문아이템들이_비어있을_경우_예외를_던진다() {
        assertThatThrownBy(
            () -> new OrderLineItems(List.of()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문아이템들을_생성할_수_있다() {
        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
            ProductFixture.createFired());
        OrderLineItem orderLineItem = StoreOrderFixture.createOrderLineItem(menu);

        assertThatNoException().isThrownBy(() -> new OrderLineItems(List.of(orderLineItem)));
    }
}