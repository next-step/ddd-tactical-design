package kitchenpos.product.application;

import static kitchenpos.product.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.common.name.Name;
import kitchenpos.common.price.Price;
import kitchenpos.product.InMemoryProductRepository;
import kitchenpos.product.tobe.domain.entity.Product;
import kitchenpos.product.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final Product expected = createProductRequest("후라이드", 16_000L);
        final Product actual = productService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id).isNotNull(),
            () -> assertThat(actual.name).isEqualTo(expected.name),
            () -> assertThat(actual.price()).isEqualTo(expected.price())
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final UUID productId = productRepository.save(
            product("후라이드", 16_000L)
        ).id;
        final Product expected = changePriceRequest(15_000L);
        final Product actual = productService.changePrice(productId, expected);
        assertThat(actual.price()).isEqualTo(expected.price());
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        productRepository.save(product("후라이드", 16_000L));
        productRepository.save(product("양념치킨", 16_000L));
        final List<Product> actual = productService.findAll();
        assertThat(actual).hasSize(2);
    }

    private Product createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private Product createProductRequest(final String name, final BigDecimal price) {
        return new Product(
            null,
            new Name(name),
            new Price(price)
        );
    }

    private Product changePriceRequest(final long price) {
        return changePriceRequest(BigDecimal.valueOf(price));
    }

    private Product changePriceRequest(final BigDecimal price) {
        return new Product(
            null,
            null,
            new Price(price)
        );
    }
}
