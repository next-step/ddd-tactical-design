package kitchenpos.menus.tobe;

import kitchenpos.menus.tobe.exception.InvalidMenuProductPricePeriodException;
import kitchenpos.menus.tobe.exception.InvalidMenuProductQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuProductTest {

    @DisplayName("메뉴 상품의 가격은 0원 이상이어 한다.")
    @ValueSource(longs = {-1, -1000, -100000})
    @ParameterizedTest(name = "{index}. 메뉴 상품 가격 : `{0}`")
    void createWithNegativePrice(final long price) {
        assertThatThrownBy(
                () -> new MenuProduct(null, price, 1L, null, 1L)
        ).isInstanceOf(InvalidMenuProductPricePeriodException.class);
    }

    @DisplayName("메뉴 상품의 수량은 0개 이상이어야 한다.")
    @ValueSource(longs = {-1, -1000, -100000})
    @ParameterizedTest(name = "{index}. 메뉴 상품 수량 : `{0}`")
    void createWithNegativeQuantity(final long quantity) {
        assertThatThrownBy(
                () -> new MenuProduct(null, 1_000L, quantity, null, 1L)
        ).isInstanceOf(InvalidMenuProductQuantityException.class);
    }

    @DisplayName("메뉴 상품을 생성할 수 있다.")
    @CsvSource(value = {"1_000:1", "1_000:2", "2_000:2"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 메뉴 상품 가격 : `{0}`, 메뉴 상품 수량 : `{1}`")
    void create(final long price, final long quantity) {
        final MenuProduct menuProduct = new MenuProduct(null, price, quantity, null, 1L);

        assertThat(menuProduct).isNotNull();
    }

    @DisplayName("메뉴 상품은 가격과 수량을 곱한 금액을 계산한다.")
    @CsvSource(value = {"1_000:1:1_000", "1_000:2:2_000", "2_000:2:4_000"}, delimiter = ':')
    @ParameterizedTest(name = "{index}. 메뉴 상품 가격 : `{0}`, 메뉴 상품 수량 : `{1}`")
    void amount(final long price, final long quantity, final long expected) {
        final MenuProduct menuProduct = new MenuProduct(null, price, quantity, null, 1L);

        assertThat(menuProduct.amount()).isEqualTo(expected);
    }
}
