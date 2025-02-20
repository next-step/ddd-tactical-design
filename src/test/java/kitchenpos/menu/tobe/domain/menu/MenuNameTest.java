package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.tobe.Profanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuNameTest {

    @Test
    @DisplayName("정상적인 메뉴 이름으로 MenuName 객체를 생성한다")
    void createMenuName() {
        // given
        // 일반적인 한식 메뉴 이름으로 테스트를 진행합니다
        String validName = "김치찌개";
        Profanities profanities = mock(Profanities.class);
        when(profanities.contains(anyString())).thenReturn(false);

        // when
        MenuName menuName = new MenuName(validName, profanities);

        // then
        // getName()을 통해 저장된 이름이 올바른지 확인합니다
        assertThat(menuName.getName()).isEqualTo(validName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    @DisplayName("메뉴 이름이 비어있거나 공백문자로만 이루어진 경우 예외가 발생한다")
    void validateBlankName(String blankName) {
        // given
        Profanities profanities = mock(Profanities.class);

        // when & then
        // isBlank() 메서드가 검증하는 다양한 공백 케이스들을 테스트합니다
        assertThatThrownBy(() -> new MenuName(blankName, profanities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 이름은 필수입니다.");
    }

    @Test
    @DisplayName("메뉴 이름이 null인 경우 예외가 발생한다")
    void validateNullName() {
        // given
        Profanities profanities = mock(Profanities.class);

        // when & then
        assertThatThrownBy(() -> new MenuName(null, profanities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 이름은 필수입니다.");
    }

    @Test
    @DisplayName("메뉴 이름에 비속어가 포함된 경우 예외가 발생한다")
    void validateProfanity() {
        // given
        String nameWithProfanity = "부적절한 메뉴 이름";
        Profanities profanities = mock(Profanities.class);
        // 비속어가 포함된 것으로 가정하여 테스트합니다
        when(profanities.contains(nameWithProfanity)).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> new MenuName(nameWithProfanity, profanities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비속어가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("같은 이름을 가진 MenuName 객체들은 동등하다")
    void testEquals() {
        // given
        // 두 개의 MenuName 객체를 같은 이름으로 생성합니다
        String name = "김치찌개";
        Profanities profanities = mock(Profanities.class);
        when(profanities.contains(anyString())).thenReturn(false);

        MenuName menuName1 = new MenuName(name, profanities);
        MenuName menuName2 = new MenuName(name, profanities);

        // when & then
        // equals와 hashCode가 모두 동일한지 검증합니다
        assertThat(menuName1)
                .isEqualTo(menuName2)
                .hasSameHashCodeAs(menuName2);
    }

    @Test
    @DisplayName("서로 다른 이름을 가진 MenuName 객체들은 동등하지 않다")
    void testNotEquals() {
        // given
        Profanities profanities = mock(Profanities.class);
        when(profanities.contains(anyString())).thenReturn(false);

        MenuName menuName1 = new MenuName("김치찌개", profanities);
        MenuName menuName2 = new MenuName("된장찌개", profanities);

        // when & then
        assertThat(menuName1).isNotEqualTo(menuName2);
    }

    @Test
    @DisplayName("MenuName 객체들이 Set에서 올바르게 동작한다")
    void testSetBehavior() {
        // given
        Profanities profanities = mock(Profanities.class);
        when(profanities.contains(anyString())).thenReturn(false);

        MenuName menuName1 = new MenuName("김치찌개", profanities);
        MenuName menuName2 = new MenuName("김치찌개", profanities);
        MenuName menuName3 = new MenuName("된장찌개", profanities);

        Set<MenuName> menuNames = new HashSet<>();

        // when
        // 같은 이름의 메뉴를 여러 번 추가해도 하나만 저장되어야 합니다
        menuNames.add(menuName1);
        menuNames.add(menuName2);
        menuNames.add(menuName3);

        // then
        assertThat(menuNames)
                .hasSize(2)
                .contains(menuName1, menuName3);
    }
}