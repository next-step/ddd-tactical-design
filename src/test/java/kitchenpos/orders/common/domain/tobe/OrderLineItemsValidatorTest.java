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

@DisplayName("OrderLineItemsValidator")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderLineItemsValidatorTest {

    private final OrderLineItemsValidator orderLineItemsValidator = new OrderLineItemsValidator();

    @Test
    void 주문아이템들이_null일_경우_검증을_실패한다() {
        assertThatThrownBy(
            () -> orderLineItemsValidator.validate(null, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문아이템들이_비어있을_경우_검증을_실패한다() {
        assertThatThrownBy(
            () -> orderLineItemsValidator.validate(List.of(), List.of()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문아이템들에_메뉴가_중복되면_검증을_실패한다() {
        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
            ProductFixture.createFired());
        OrderLineItem orderLineItem = StoreOrderFixture.createOrderLineItem(menu);

        assertThatThrownBy(() -> orderLineItemsValidator.validate(
            List.of(orderLineItem, orderLineItem),
            List.of(menu))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문아이템들의_검증을_성공한다() {
        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(),
            ProductFixture.createFired());
        OrderLineItem orderLineItem = StoreOrderFixture.createOrderLineItem(menu);

        assertThatNoException().isThrownBy(() -> orderLineItemsValidator.validate(
            List.of(orderLineItem), List.of(menu))
        );
    }
}