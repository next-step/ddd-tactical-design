package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("상품 가격")
class PriceTest {

    @DisplayName("상품 가격을 생성한다")
    @ParameterizedTest
    @ValueSource(strings = {"10000", "20000"})
    void createPrice(BigDecimal price) {
        assertDoesNotThrow(() -> new Price(price));
    }

    @DisplayName("가격은 0원보다 커야한다.")
    @ParameterizedTest
    @ValueSource(strings = "-1")
    void createNegativePrice(BigDecimal negativePrice) {
        assertThatThrownBy(() -> new Price(negativePrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0원 이하일 수 없습니다.");
    }

    @DisplayName("가격을 변경한다.")
    @ParameterizedTest
    @ValueSource(strings = "10000")
    void changePrice(BigDecimal changePrice) {
        Price price = new Price(BigDecimal.valueOf(20000));
        price.change(changePrice);
        assertThat(price).isEqualTo(new Price(changePrice));
    }

    @DisplayName("상품 가격을 0원보다 적은 금액으로 변경할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = "-1")
    void changeNegativePrice(BigDecimal changePrice) {
        Price price = new Price(BigDecimal.TEN);
        assertThatThrownBy(() -> price.change(changePrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0원 이하일 수 없습니다.");
    }

}
