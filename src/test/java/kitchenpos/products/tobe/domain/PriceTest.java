package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductPriceTest {

    @DisplayName("상품 가격을 생성한다")
    @Test
    @ValueSource(ints = {0, 100})
    @ParameterizedTest
    void createProductPrice(int price) {
        assertDoesNotThrow(() -> new ProductPrice(price));
    }

    @DisplayName("가격은 음수일 수 없다.")
    @Test
    @ValueSource(ints = {0, 100})
    @ParameterizedTest
    void createProductPrice1(int price) {
        assertThatThrownBy(() -> new ProductPrice(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격을 변경한다.")
    @Test
    void changePrice() {
        Price price = new Price(10000);
        price.change(20000);

        assertThat(price).isEqualTo(new Price(20000));
    }

}

