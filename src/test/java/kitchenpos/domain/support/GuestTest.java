package kitchenpos.domain.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class GuestTest {
    
    @DisplayName("손님을 생성한다")
    @Test
    void constructor() {
        Guest guest = new Guest();
        assertThat(guest.getCount()).isZero();
    }

    @DisplayName("손님의 수가 음수이면 생성을 실패한다")
    @Test
    void constructor_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Guest(-1));
    }
}
