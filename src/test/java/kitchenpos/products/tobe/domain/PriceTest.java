package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class PriceTest {

    @DisplayName("가격은 null 값이 들어오면 안된다.")
    @NullSource
    @ParameterizedTest
    void nullPrice(Long price) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(price));
    }

    @DisplayName("가격은 0보다 작으면 안된다.")
    @Test
    void test() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(-1L));
        assertThatNoException().isThrownBy(() -> new Price(0L));
    }

}
