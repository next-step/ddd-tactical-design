package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.intrastructure.ProductClientImpl;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductTest {


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

    @DisplayName("MenuProduct를 생성할 수 있다.")
    @Test
    void create() {
        final MenuProduct menuProduct = MenuProduct.from(product.getId(), 1L, productClient);
        assertAll(
                () -> assertNull(menuProduct.getSeq()),
                () -> assertNotNull(menuProduct.getProductId()),
                () -> assertEquals(menuProduct.getQuantity(), 1L),
                () -> assertEquals(menuProduct.getPrice(), product.getPriceValue())
        );
    }

    @DisplayName("MenuProduct 생성 시 Quantity가 0보다 작으면 예외가 발생한다.")
    @Test
    void createWithNegativeQuantity() {
        assertThatThrownBy(() -> MenuProduct.from(product.getId(), -1L, productClient))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProduct Price를 변경할 수 있다.")
    @Test
    void changePrice() {
        final MenuProduct menuProduct = MenuProduct.from(product.getId(), 1L, productClient);
        menuProduct.changePrice(BigDecimal.valueOf(20000));
        assertEquals(menuProduct.getPrice(), BigDecimal.valueOf(20000));
    }

    @DisplayName("MenuProduct의 Price와 Quantity을 곱한 amount를 반환할 수 있다.")
    @Test
    void calculateAmount() {
        final MenuProduct menuProduct = MenuProduct.from(product.getId(), 2L, productClient);
        assertEquals(menuProduct.calculateAmount(), BigDecimal.valueOf(32000L));
    }

    @DisplayName("특정 productId가 MenuProduct의 productId와 동일한지 여부를 확인할 수 있다.")
    @Test
    void isSameProductId() {
        final UUID productId = product.getId();
        final MenuProduct menuProduct = MenuProduct.from(product.getId(), 2L, productClient);
        assertThat(menuProduct.isSameProductId(productId)).isTrue();
    }
}
