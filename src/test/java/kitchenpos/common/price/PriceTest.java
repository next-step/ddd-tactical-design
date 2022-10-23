package kitchenpos.common.price;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("Price는 0 이상의 정수일 수 있다.")
    @ValueSource(longs = {
        0, 59031154, 1793947942, 40534364, 1832014351,
        629019133, 1313690456, 1777618747, 314068119, 256424390,
    })
    @ParameterizedTest
    void ncevwfii(final long price) {
        assertThatNoException().isThrownBy(() -> new Price(price));
    }

    @DisplayName("Price는 0 이상의 실수일 수 있다.")
    @ValueSource(doubles = {
        0.0, 3792.94, 6223.72, 7920.30, 300.13,
        363.69, 3078.50, 1312.45, 2145.62, 7028.71,
    })
    @ParameterizedTest
    void ccbiskwr(final double price) {
        assertThatNoException().isThrownBy(() -> new Price(price));
    }

    @DisplayName("Price는 null일 수 없다.")
    @Test
    void fgktnvgm() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(null));
    }

    @DisplayName("Price는 음의 정수일 수 없다.")
    @ValueSource(longs = {
        -137303079, -1004122297, -273047245, -1923277843, -507393273,
        -849940561, -1049949352, -162065211, -585202570, -1898829030,
    })
    @ParameterizedTest
    void xjnyumit(final long price) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(price));
    }

    @DisplayName("Price는 음의 실수일 수 없다.")
    @ValueSource(doubles = {
        -7132.27, -576.64, -823.34, -5673.90, -3244.43,
        -8709.46, -1575.65, -1861.43, -772.70, -8426.98,
    })
    @ParameterizedTest
    void xjnyumit(final double price) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(price));
    }
}
