package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @DisplayName("가격을 생성한다.")
    @ValueSource(ints = {0, 10000})
    @ParameterizedTest
    void create(int price) {
        assertDoesNotThrow(() -> new Price(price));
    }

    @DisplayName("가격은 음수일 수 없다.")
    @Test
    void createWithNegative() {
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격을 변경한다.")
    @Test
    void change() {
        Price price = new Price(10000);

        price.change(20000);

        assertThat(price).isEqualTo(new Price(20000));
    }

    @DisplayName("가격을 음수로 변경할 수 없다.")
    @Test
    void changeWithNegative() {
        Price price = new Price(10000);

        assertThatThrownBy(() -> price.change(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
