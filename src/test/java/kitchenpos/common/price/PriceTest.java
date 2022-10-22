package kitchenpos.common.price;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("Constructor")
    @Nested
    class Edzphedq {

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

    @DisplayName("add()")
    @Nested
    class Sbzrrxpt {

        @DisplayName("더한 결과가 정확해야 한다.")
        @ValueSource(doubles = {
            6371.44, 4658.70, 885.35, 331.00, 3989.34,
            758.08, 7988.55, 8136.68, 2548.28, 6164.34,
        })
        @ParameterizedTest
        void nzbkndwy(final double price) {
            final Price initial = new Price(1000);
            final Price augend = new Price(price);

            final Price actual = initial.add(augend);

            final BigDecimal expected = BigDecimal.valueOf(1000).add(BigDecimal.valueOf(price));

            Assertions.assertAll(
                () -> assertThat(actual.value).isEqualTo(expected),
                () -> assertThat(actual).isEqualTo(new Price(expected))
            );
        }
    }

    @DisplayName("multiply()")
    @Nested
    class Ducojvvs {

        @DisplayName("곱한 결과가 정확해야 한다.")
        @ValueSource(doubles = {
            5389.35, 4783.53, 6250.83, 3838.19, 5885.34,
            4750.17, 9516.70, 9372.23, 7657.34, 5658.99,
        })
        @ParameterizedTest
        void njzemqit(final double price) {
            final Price initial = new Price(price);
            final BigDecimal multiplicand = BigDecimal.valueOf(2);

            final Price actual = initial.multiply(multiplicand);

            final BigDecimal expected = BigDecimal.valueOf(price).multiply(multiplicand);

            Assertions.assertAll(
                () -> assertThat(actual.value).isEqualTo(expected),
                () -> assertThat(actual).isEqualTo(new Price(expected))
            );
        }
    }
}
