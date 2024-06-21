package kitchenpos.orders.common.domain.tobe;

import kitchenpos.orders.common.domain.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MenuQuantity")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuQuantityTest {

    @ParameterizedTest
    @CsvSource(value = {"DELIVERY", "TAKEOUT"})
    void 매장주문이_아닌_주문의_메뉴수량은_음수가_될_수_없다(OrderType orderType) {
        assertThatThrownBy(() -> new MenuQuantity(orderType, -2))
                .isInstanceOf(IllegalArgumentException.class);
    }
}