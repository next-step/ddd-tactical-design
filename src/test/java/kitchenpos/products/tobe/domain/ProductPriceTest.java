package kitchenpos.products.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("상품 가격")
class ProductPriceTest {

    @DisplayName("더하기")
    @Test
    void add() {
        ProductPrice ten = createProductPrice(10);
        ProductPrice hundred = createProductPrice(100);
        ProductPrice add = ten.add(hundred);
        assertThat(add).isEqualTo(createProductPrice(110));
    }

    @DisplayName("곱하기")
    @Test
    void multiply() {
        ProductPrice ten = createProductPrice(10);
        ProductPrice multiply = ten.multiply(10);
        assertThat(multiply).isEqualTo(createProductPrice(100));
    }

    private ProductPrice createProductPrice(long input){
        return  new ProductPrice(BigDecimal.valueOf(input));
    }
}
