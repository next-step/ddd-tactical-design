package kitchenpos.domain.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class NameTest {

    @DisplayName("이름을 생성한다")
    @Test
    void constructor() {
        String name = "이름";
        assertThat(new Name(name)).isEqualTo(new Name(name));
    }

    @DisplayName("이름이 Null이면 생성을 실패한다")
    @Test
    void constructor_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Name(null));
    }

    @DisplayName("이름에 비속어가 포함되면 생성을 실패한다")
    @Test
    void constructor_blackWord_fail() {
        String name = "비속어이름";
        assertThatIllegalArgumentException().isThrownBy(() -> new Name(name, (text) -> true));
    }
}
