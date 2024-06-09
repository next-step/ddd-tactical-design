package kitchenpos.products.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

public class ProductPriceTest {


    @Test
    @DisplayName("상품 가격이 null이면 예외 발생")
    void shouldThrowExceptionWhenPriceIsNull() {
        assertThatCode(() -> new ProductPrice(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품가격이 음수면 예외 발생")
    void shouldThrowExceptionWhenPriceIsNegative() {
        assertThatCode(() -> new ProductPrice(BigDecimal.valueOf(-1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ValueSource(strings = {"1","0"})
    @ParameterizedTest
    @DisplayName("가격이 정상일 때 정상동작")
    void shouldNotThrowExceptionWhenPriceIsGood(String price) {
        assertDoesNotThrow(() -> new ProductPrice(new BigDecimal(price)));
    }

    @Test
    @DisplayName("가격이 같으면 같은 객체")
    void shouldReturnTrueWhenComparingEqualProductPrices() {
        BigDecimal price = new BigDecimal(3);
        ProductPrice productPrice1 = new ProductPrice(price);
        ProductPrice productPrice2 = new ProductPrice(price);
        assertThat(productPrice1).isEqualTo(productPrice2);
    }

    @Test
    @DisplayName("가격이 다르면 다른 객체")
    void shouldReturnFalseWhenComparingDifferentProductPrices() {
        ProductPrice productPrice1 = new ProductPrice(new BigDecimal(3));
        ProductPrice productPrice2 = new ProductPrice(new BigDecimal(4));
        assertThat(productPrice1).isNotEqualTo(productPrice2);
    }
}