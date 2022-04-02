package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class MoneyTest {

    @ParameterizedTest
    @NullSource
    void notNull(BigDecimal value) {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Money(value));
    }

    @Test
    void lessThan() {
        final Money one = new Money(1);
        assertAll(
            () -> assertThat(Money.ZERO.isLessThan(one)).isTrue(),
            () -> assertThat(Money.ZERO.isLessThan(Money.ZERO)).isFalse()
        );
    }
}
