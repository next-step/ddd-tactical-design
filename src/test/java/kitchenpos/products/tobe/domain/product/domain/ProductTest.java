package kitchenpos.products.tobe.domain.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    @DisplayName("정상적으로 생성되는 것 테스트.")
    @Test
    void create() {
        Product result = new Product(1L, "양념치킨", BigDecimal.valueOf(16_000));
        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getName()).isEqualTo("양념치킨"),
                () -> assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(16_000))
        );
    }

    @DisplayName("상품의 가격이 올바르지 상품을 만들 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void createFail(final BigDecimal price) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Product(1L,"양념치킨", price));
    }
}