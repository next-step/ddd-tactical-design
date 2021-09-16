package kitchenpos.menus.tobe.domain;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.common.infra.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MenuNameTest {
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakeProfanities();
    }

    @DisplayName("메뉴 이름은 비어있을 수 없다.")
    @Test
    void emptyName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuName(null, profanities))
                .withMessage("메뉴 이름은 필수값입니다.");
    }

    @DisplayName("적절하지 않은 메뉴 이름은 validation 에 실패한다.")
    @Test
    void profanityName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuName("욕설", profanities))
                .withMessage("적절하지 않은 메뉴 이름입니다.");
    }
}
