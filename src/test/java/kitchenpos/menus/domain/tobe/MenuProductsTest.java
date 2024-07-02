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

@DisplayName("MenuProducts")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuProductsTest {

    @Test
    void 메뉴상품들이_null일_경우_예외를_던진다() {
        assertThatThrownBy(
                () -> new MenuProducts(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들이_비어있을_경우_예외를_던진다() {
        assertThatThrownBy(
                () -> new MenuProducts(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴상품들을_생성할_수_있다() {
        Product product = ProductFixture.createFired(20_000L);
        assertThatNoException().isThrownBy(() -> new MenuProducts(
                List.of(new MenuProduct(product, 2)))
        );
    }
}