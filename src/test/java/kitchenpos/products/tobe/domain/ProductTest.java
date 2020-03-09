package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductTest {

    @DisplayName("상품 정상 생성")
    @Test
    void create() {
        String productName = "간장 치킨";
        BigDecimal productPrice = BigDecimal.TEN;

        Product product = new Product(productName, productPrice);

        assertThat(product.getName()).isEqualTo(productName);
        assertThat(product.getPrice()).isEqualTo(productPrice);
    }

    @DisplayName("상품 이름이 올바르지 않을시 상품 생성 에러")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullAndEmptySource
    void create_fail_by_productName(String productName) {
        BigDecimal productPrice = BigDecimal.TEN;

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Product(productName, productPrice));
    }

    @DisplayName("상품 가격이 올바르지 않을시 상품 생성 에러")
    @ParameterizedTest
    @ValueSource(strings = {"-1000"})
    @NullSource
    void create_fail_by_productPrice(BigDecimal productPrice) {
        String productName = "간장 치킨";

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Product(productName, productPrice));
    }
}