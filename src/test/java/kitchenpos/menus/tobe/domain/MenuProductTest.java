package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.application.FakeProductClient;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.ProductClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴구성상품")
class MenuProductTest {
    private ProductRepository productRepository;
    private ProductClient productClient;
    private UUID productId;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productClient = new FakeProductClient(productRepository);
        Product product = productRepository.save(product());
        productId = product.getId();
    }

    @DisplayName("[성공] 메뉴구성상품을 생성한다.")
    @Test
    void create() {
        MenuProduct actual = MenuProduct.from(productId, 2L, productClient);

        assertAll(
                () -> assertThat(actual.getSeq()).isNull(),
                () -> assertThat(actual.getProductId()).isEqualTo(productId),
                () -> assertThat(actual.getPrice()).isEqualTo(ProductPrice.from(16_000L)),
                () -> assertThat(actual.getQuantity()).isEqualTo(2L)
        );
    }

    @DisplayName("[실패] 메뉴구성상품의 수량은 0개 이상이다")
    @ValueSource(longs = {-1, -2, -100, -9999})
    @ParameterizedTest
    void fail_quantity(long quantity) {
        assertThatThrownBy(() -> MenuProduct.from(productId, quantity, productClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[성공] 메뉴구성상품의 가격을 변경한다.")
    @Test
    void changeProductPrice() {
        ProductPrice price = ProductPrice.from(20_000L);
        MenuProduct menuProduct = MenuProduct.from(productId, 2L, productClient);

        menuProduct.changeProductPrice(price);

        assertAll(
                () -> assertThat(menuProduct.getPrice()).isEqualTo(ProductPrice.from(20_000L))
        );
    }

    @DisplayName("[성공] 메뉴구성상품의 총 가격(상품의 가격 * 수량)을 구할 수 있다.")
    @Test
    void totalPrice() {
        MenuProduct menuProduct = menuProduct();

        assertAll(
                () -> assertThat(menuProduct.totalPrice()).isEqualTo(BigDecimal.valueOf(32_000L))
        );
    }

}
