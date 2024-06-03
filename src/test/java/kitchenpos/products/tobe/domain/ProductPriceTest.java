package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {

    @Test
    @DisplayName("성공")
    void success() {
        //given
        int price = 18000;

        //when
        ProductPrice productPrice = new ProductPrice(18000);

        //then
        assertThat(productPrice.getValue()).isEqualTo(price);

    }

    @Test
    @DisplayName("0미만의 값을 올수 없다.")
    void canNotHaveMinus() {
        assertThatThrownBy(() -> new ProductPrice(-1))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ProductPrice(-1000))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
