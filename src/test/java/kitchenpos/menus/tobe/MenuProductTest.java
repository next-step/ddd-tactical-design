package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.InvalidMenuProductPricePeriodException;
import kitchenpos.menus.tobe.exception.InvalidMenuProductQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuProductTest {

    @DisplayName("메뉴 상품의 가격은 0원 이상이어 한다.")
    @ParameterizedTest(name = "{index}. 메뉴 상품 가격 : `{0}`")
    @ValueSource(strings = {"-1", "-1000", "-100000"})
    void createWithNegativePrice(final long price) {
        assertThatThrownBy(
                () -> new MenuProduct(null, price, 1L, 1L)
        ).isInstanceOf(InvalidMenuProductPricePeriodException.class);
    }

    @DisplayName("메뉴 상품의 수량은 0개 이상이어야 한다.")
    @ParameterizedTest(name = "{index}. 메뉴 상품 수량 : `{0}`")
    @ValueSource(strings = {"-1", "-1000", "-100000"})
    void createWithNegativeQuantity(final long quantity) {
        assertThatThrownBy(
                () -> new MenuProduct(null, 1_000L, quantity, 1L)
        ).isInstanceOf(InvalidMenuProductQuantityException.class);
    }
}
