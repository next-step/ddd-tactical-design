package kitchenpos.menus.tobe.domain;

import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴 상품(MenuProduct)은")
class MenuProductTest {
    @Test
    @DisplayName("생성할 수 있다.")
    void create() {
        final UUID productId = randomUUID();
        final Quantity quantity = new Quantity(1L);

        final MenuProduct menuProduct = new MenuProduct(productId, quantity);

        assertThat(menuProduct).isEqualTo(new MenuProduct(productId, quantity));
    }

    @Test
    @DisplayName("상품으로부터 가격을 설정하고 계산할 수 있다.")
    void loadProduct() {
        final Product product = ProductFixture.상품();
        final long quantity = 2L;
        final MenuProduct menuProduct = MenuFixture.메뉴상품(product.getId(), quantity);

        menuProduct.loadProduct(product);
        final BigDecimal expected = menuProduct.calculate();

        assertThat(expected).isEqualTo(product.getPrice().calculate(new Quantity(quantity)));
    }
}
