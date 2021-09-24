package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("메뉴 금액(Amount)은")
class AmountTest {
    @Test
    @DisplayName("생성할 수 있다.")
    void create() {
        final BigDecimal value = BigDecimal.valueOf(1000);
        final Amount amount = new Amount(value);

        assertThat(amount).isEqualTo(new Amount(value));
    }

    @ParameterizedTest
    @ValueSource(strings = "-1000")
    @DisplayName("0원 미만이면 IllegalArgumentException이 발생한다.")
    void create(BigDecimal value) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new Amount(value);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }
}
