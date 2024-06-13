package kitchenpos.products.tobe.domain.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.common.purgomalum.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.repository.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CreateProductTest {
    CreateProduct createProduct;

    @BeforeEach
    public void setup() {
        ProductRepository productRepository = new InMemoryProductRepository();
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        this.createProduct = new DefaultCreateProduct(purgomalumClient, productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        final ProductCreateDto expected = createProductRequest("후라이드", 16_000L);
        final Product actual = createProduct.execute(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        final ProductCreateDto expected = createProductRequest("후라이드", price);
        assertThatThrownBy(() -> createProduct.execute(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        final ProductCreateDto expected = createProductRequest(name, 16_000L);
        assertThatThrownBy(() -> createProduct.execute(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private ProductCreateDto createProductRequest(final String name, final long price) {
        return createProductRequest(name, BigDecimal.valueOf(price));
    }

    private ProductCreateDto createProductRequest(final String name, final BigDecimal price) {
        return new ProductCreateDto(name, price);
    }
}
