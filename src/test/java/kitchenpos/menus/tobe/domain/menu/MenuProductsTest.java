package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.intrastructure.ProductClientImpl;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    private ProductRepository productRepository;
    private ProductClient productClient;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        product = product();
        productRepository.save(product);
        productClient = new ProductClientImpl(productRepository);
    }

    @DisplayName("MenuProducts를 생성할 수 있다.")
    @Test
    void create() {
        final MenuProducts menuProducts = MenuProducts.from(
                List.of(
                        new MenuProductMaterial(product.getId(), 2L),
                        new MenuProductMaterial(product.getId(), 2L)
                ),
                productClient
        );
        assertThat(menuProducts.getMenuProducts()).hasSize(2);
    }

    @DisplayName("MenuProducts 생성 시 MenuProduct가 없으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createWithEmptyMenuProducts(List<MenuProductMaterial> menuProductMaterials) {
        assertThatThrownBy(() -> MenuProducts.from(menuProductMaterials, productClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProducts의 내부에 ProductId 와 일치하는 MenuProduct의 가격을 변경할 수 있다.")
    @Test
    void changeMenuProductPrice() {
        final MenuProducts menuProducts = MenuProducts.from(
                List.of(
                        new MenuProductMaterial(product.getId(), 1L),
                        new MenuProductMaterial(product.getId(), 2L)
                ),
                productClient
        );
        menuProducts.changeMenuProductsPrice(product.getId(), BigDecimal.valueOf(30000L));
        assertThat(menuProducts.getMenuProducts()).extracting(MenuProduct::getPrice)
                .containsExactly(BigDecimal.valueOf(30000L), BigDecimal.valueOf(30000L));
    }

    @DisplayName("MenuProducts의 모든 amount를 합한 결과를 반환할 수 있다.")
    @Test
    void totalAmount() {
        final MenuProducts menuProducts = MenuProducts.from(
                List.of(
                        new MenuProductMaterial(product.getId(), 1L),
                        new MenuProductMaterial(product.getId(), 2L)
                ),
                productClient
        );
        assertThat(menuProducts.totalAmount()).isEqualTo(MenuPrice.from(BigDecimal.valueOf(48000L)));
    }
}
