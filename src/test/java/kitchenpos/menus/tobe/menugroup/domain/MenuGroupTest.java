package kitchenpos.menus.tobe.menugroup.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MenuGroupTest {

    @Test
    @DisplayName("메뉴 그룹을 생성할 수 있다.")
    void create() {
        final UUID id = UUID.randomUUID();
        final String name = "메뉴 그룹";

        final MenuGroup menuGroup = new MenuGroup(id, name);

        assertThat(menuGroup).isEqualTo(new MenuGroup(id, name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴 그룹의 이름이 비어있으면 IllegalArgumentException이 발생한다.")
    void create(String name) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> new MenuGroup(name);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(throwingCallable);
    }
}
