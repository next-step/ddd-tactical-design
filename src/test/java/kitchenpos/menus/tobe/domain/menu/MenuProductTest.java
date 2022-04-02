package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;
import kitchenpos.common.domain.Money;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("MenuProduct 는")
class MenuProductTest {

    private final ProductId productId = new ProductId(UUID.randomUUID());

    @DisplayName("추가할 수 있다.")
    @Nested
    class 추가할_수_있다 {

        @DisplayName("상품이 없다면 추가할 수 없다")
        @Test
        void 상품이_없다면_추가할_수_없다() {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new MenuProduct(null, Money.ZERO, 0));
        }

        @DisplayName("수량이 0미만이면 추가할 수 없다")
        @Test
        void 수량이_0미만이면_추가할_수_없다() {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new MenuProduct(productId, Money.ZERO, -1));
        }

        @DisplayName("상품이 있고 수량이 0이상이면 추가할 수 있다")
        @ParameterizedTest(name = "{0}인 경우")
        @ValueSource(longs = {0, 1})
        void 수량이_0이상이면_추가할_수_있다(long quantity) {
            assertDoesNotThrow(() -> new MenuProduct(productId, Money.ZERO, quantity));
        }
    }
}
