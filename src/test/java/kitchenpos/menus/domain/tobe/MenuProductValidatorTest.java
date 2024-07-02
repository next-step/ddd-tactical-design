package kitchenpos.menus.domain.tobe;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.products.domain.tobe.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("MenuProductValidator")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuProductValidatorTest {

    private final MenuProductsValidator menuProductValidator = new MenuProductsValidator();

    @Test
    void 메뉴상품들이_null일_경우_검증을_실패한다() {
        assertThatThrownBy(
            () -> menuProductValidator.validate(null, null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들이_비어있을_경우_검증을_실패한다() {
        assertThatThrownBy(
            () -> menuProductValidator.validate(List.of(), List.of()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들에_메뉴가_중복되면_검증을_실패한다() {
        Product product = ProductFixture.createFired(20_000L);
        MenuProduct menuProduct = new MenuProduct(product, 2);

        assertThatThrownBy(
            () -> menuProductValidator.validate(List.of(menuProduct, menuProduct),
                List.of(product)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들의_검증을_성공한다() {
        Product product = ProductFixture.createFired(20_000L);
        assertThatNoException().isThrownBy(() -> menuProductValidator.validate(
            List.of(new MenuProduct(product, 2)), List.of(product))
        );
    }
}