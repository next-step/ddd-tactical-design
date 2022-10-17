package kitchenpos.eatinorders.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuestCountTest {

    @Test
    @DisplayName("손님수 객체 생성이 가능하다")
    void constructor() {
        final GuestCount expected = new GuestCount(5);
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("손님수는 0이상이어야 한다")
    @ValueSource(ints = { -1 })
    void constructor_with_negative_value(final int value) {
        assertThatThrownBy(() -> new GuestCount(value))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
