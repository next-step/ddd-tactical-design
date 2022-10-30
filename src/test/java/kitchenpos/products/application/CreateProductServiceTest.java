package kitchenpos.products.application;

import kitchenpos.common.domain.infra.PurgomalumValidator;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CreateProductServiceTest {
    private ProductRepository productRepository;
    private PurgomalumValidator purgomalumValidator;
    private CreateProductService createProductService;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        purgomalumValidator = new FakePurgomalumValidator();
        createProductService = new CreateProductService(purgomalumValidator, productRepository);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        BigDecimal price = BigDecimal.valueOf(16_000L);
        String name = "후라이드";
        final Product actual = createProductService.create(price, name);
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName().getName()).isEqualTo(name),
                () -> assertThat(actual.getPrice().getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 예외가 발생한다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        String name = "후라이드";
        assertThatThrownBy(() -> createProductService.create(price, name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 예외가 발생한다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        BigDecimal price = BigDecimal.valueOf(16_000L);
        assertThatThrownBy(() -> createProductService.create(price, name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}