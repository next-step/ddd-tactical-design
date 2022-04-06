package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.UUID;
import kitchenpos.common.domain.Money;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("MenuProducts 는")
class MenuProductsTest {
    private final ProductId productId = new ProductId(UUID.randomUUID());
    private final MenuProduct 천원메뉴상품 = new MenuProduct(productId, 1_000, 1);
    private final MenuProduct 이천원메뉴상품 = new MenuProduct(productId, 1_000, 2);

    @DisplayName("생성할 수 있다")
    @Nested
    class 생성할_수_있다 {

        @DisplayName("비어있다면 생성할 수 없다")
        @ParameterizedTest(name = "{0}인 경우")
        @NullAndEmptySource
        void 비어있다면_생성할_수_없다(List<MenuProduct> elements) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new MenuProducts(elements));
        }

        @DisplayName("비어있지 않다면 생성할 수 있다")
        @Test
        void 비어있지_않다면_생성할_수_있다() {
            assertDoesNotThrow(() -> new MenuProducts(천원메뉴상품, 이천원메뉴상품));
        }
    }

    @DisplayName("가격을 계산할 수 있다")
    @Test
    void 가격을_계산할_수_있다() {
        final MenuProducts menuProducts = new MenuProducts(천원메뉴상품, 이천원메뉴상품);

        assertThat(menuProducts.calculatePrice()).isEqualTo(new Money(3_000));
    }
}
