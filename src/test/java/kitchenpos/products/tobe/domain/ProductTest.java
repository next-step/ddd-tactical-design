package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @DisplayName("상품은 번호,이름,가격을 가진다.")
    @Test
    void create() {
        //given
        String name = "치킨";
        BigDecimal price = BigDecimal.valueOf(1000);

        //when
        Product product = new Product(name, price);

        //then
        assertThat(product).isNotNull();
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"-100", "-10", "-0.1"})
    void priceValidate(final BigDecimal price) {
        //given
        String name = "치킨";

        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Product(name, price));
    }
}