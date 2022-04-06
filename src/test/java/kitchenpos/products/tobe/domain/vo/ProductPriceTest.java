package kitchenpos.products.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class ProductPriceTest {

    @DisplayName("상품의 가격은 비어있을 수 없다.")
    @ParameterizedTest
    @NullSource
    void can_not_be_null_price(final BigDecimal price) {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProductPrice(price));
    }

    @DisplayName("상품 가격은 0원 보다 작을 수 없다.")
    @Test
    void can_not_be_less_than_zero_price() {
        //given
        //when
        //then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> createProductPrice(-1L));
    }

    @DisplayName("hashCode")
    @Test
    void hashCodeTest() {
        //given
        ProductPrice sameResult_1 = createProductPrice(19_000L);
        ProductPrice sameResult_2 = createProductPrice(19_000L);
        ProductPrice other_result_1 = createProductPrice(18_000L);

        //when
        //then
        assertAll(
            () -> assertThat(sameResult_1.hashCode()).isEqualTo(sameResult_1.hashCode()),
            () -> assertThat(sameResult_1.hashCode()).isEqualTo(sameResult_2.hashCode()),
            () -> assertThat(sameResult_1.hashCode()).isNotEqualTo(other_result_1.hashCode())
        );
    }

    @DisplayName("equals")
    @Test
    void equalsTest() {
        //given
        ProductPrice sameResult_1 = createProductPrice(19_000L);
        ProductPrice sameResult_2 = createProductPrice(19_000L);
        ProductPrice other_result_1 = createProductPrice(18_000L);

        //when
        //then
        assertAll(
            () -> assertThat(sameResult_1.equals(sameResult_1)).isTrue(),
            () -> assertThat(sameResult_1.equals(sameResult_2)).isTrue(),
            () -> assertThat(sameResult_1.equals(other_result_1)).isFalse()
        );
    }

    private ProductPrice createProductPrice(final long price) {
        return createProductPrice(BigDecimal.valueOf(price));
    }

    private ProductPrice createProductPrice(final BigDecimal price) {
        return new ProductPrice(price);
    }

}