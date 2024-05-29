package kitchenpos.menus.domain;

import kitchenpos.menus.tobe.domain.MenuGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

@DisplayName("메뉴 그룹 도메인 테스트")
public class MenuGroupTest {
    @Test
    @DisplayName("메뉴 그룹을 생성한다.")
    void create() {
        String 이름 = "후라이드";
        MenuGroup 메뉴_그룹 = new MenuGroup(UUID.randomUUID(), 이름);

        Assertions.assertThat(메뉴_그룹.getId()).isNotNull();
    }

    @NullAndEmptySource
    @ParameterizedTest
    @DisplayName("메뉴 그룹의 이름은 공백일 수 없다.")
    void create_exception_empty_name(String 이름) {
        Assertions.assertThatThrownBy(
                () -> new MenuGroup(UUID.randomUUID(), 이름)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
