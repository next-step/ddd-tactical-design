package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class PriceServiceTest {

    @DisplayName("가격 생성 성공")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 1000L, 999999L})
    void success(Long price) {
        assertThatNoException()
                .isThrownBy(() -> new Price(price));
    }

    @DisplayName("가격은 null이 될 수 없다.")
    @ParameterizedTest
    @NullSource
    void fail_null_price(Long price) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(price));
    }

    @DisplayName("가격은 0보다 작을 수 없다.")
    @Test
    void fail_0_price() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Price(-1L));
    }
}
