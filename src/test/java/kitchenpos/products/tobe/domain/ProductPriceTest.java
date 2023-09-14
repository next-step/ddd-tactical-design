package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LegacyProductPriceTest {
    @DisplayName("상품 가격 생성 성공")
    @ParameterizedTest
    @ValueSource(longs = {0, 10000})
    void creatingProductPrice_is_success(long requestProductPrice) {
        //when
        ProductPrice productPrice = new ProductPrice(new BigDecimal(requestProductPrice));

        //then
        assertThat(productPrice).isEqualTo(new ProductPrice(new BigDecimal(requestProductPrice)));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다")
    @Test
    void productPrice_must_exist() {
        //when && then
        assertThatThrownBy(() -> new ProductPrice(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상풍 가격은 필수로 존재해야 합니다.");

    }

    @DisplayName("상품의 가격은 0원 이상이어야 한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, -1000})
    void productPrice_should_be_greater_than_zero(long requestProductPrice) {
        //given
        BigDecimal zeroProductPrice = new BigDecimal(requestProductPrice);

        assertThatThrownBy(() -> new ProductPrice(zeroProductPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품 가격은 0원 이상이어야 합니다.");
    }
}