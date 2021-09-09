package kitchenpos.menus.tobe.domain;

import kitchenpos.common.infra.FakeProfanities;
import kitchenpos.common.infra.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MenuGroupNameTest {
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        profanities = new FakeProfanities();
    }

    @DisplayName("메뉴 그룹 이름은 비어있을 수 없다.")
    @Test
    void emptyName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuGroupName(null, profanities))
                .withMessage("메뉴 그룹 이름은 필수값입니다.");
    }

    @DisplayName("적절하지 않은 메뉴 그룹 이름은 validation 에 실패한다.")
    @Test
    void profanityName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuGroupName("욕설", profanities))
                .withMessage("적절하지 않은 메뉴 그룹 이름입니다.");
    }

    @DisplayName("메뉴 그룹 이름이 같으면, 같아야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"치킨 메뉴 그룹", "피자 메뉴 그룹"})
    void equalPrice(final String name) {
        assertThat(new MenuGroupName(name, profanities))
                .isEqualTo(new MenuGroupName(name, profanities));
    }
}
