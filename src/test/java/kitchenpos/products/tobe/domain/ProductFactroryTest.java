package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.domain.Product;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductFactroryTest {
    private ProductFactory productFactory;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
        productFactory = new ProductFactory(purgomalumClient);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        //given
        final String name = "후라이드";
        final BigDecimal price = BigDecimal.valueOf(16_000L);
        final kitchenpos.products.tobe.domain.Product product = productFactory.createProduct(name, Price.of(price));

        assertThat(product).isNotNull();
        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        //given

        //when
        //then
        assertThatThrownBy(
                () -> productFactory.createProduct("후라이드", Price.of(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        //given

        //when
        //then
        assertThatThrownBy(
                () -> productFactory.createProduct(name, Price.of(BigDecimal.valueOf(10_000L)))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
