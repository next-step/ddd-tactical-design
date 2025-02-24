package kitchenpos.menu.tobe.domain.menugroup;

import kitchenpos.menu.tobe.domain.menugroup.MenuGroupName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuGroupNameTest {
    @Test
    @DisplayName("유효한 이름으로 MenuGroupName을 생성한다")
    void createMenuGroupNameWithValidName() {
        // given
        String validName = "메인 메뉴";

        // when
        MenuGroupName menuGroupName = MenuGroupName.of(validName);

        // then
        assertThat(menuGroupName.getValue()).isEqualTo(validName);
    }

    @DisplayName("이름이 null이거나 빈 문자열인 경우 예외가 발생한다")
    @NullAndEmptySource
    @ParameterizedTest
    void cannotCreateMenuGroupNameWithNullOrEmpty(String invalidName) {
        assertThatThrownBy(() -> MenuGroupName.of(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 그룹 이름은 비어있을 수 없습니다");
    }

    @DisplayName("이름이 공백 문자로만 이루어진 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    void cannotCreateMenuGroupNameWithBlankString(String blankName) {
        assertThatThrownBy(() -> MenuGroupName.of(blankName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 그룹 이름은 비어있을 수 없습니다");
    }

    @Test
    @DisplayName("동일한 값을 가진 MenuGroupName 객체는 equals 비교에서 true를 반환한다")
    void equalMenuGroupNames() {
        // given
        MenuGroupName name1 = MenuGroupName.of("디저트");
        MenuGroupName name2 = MenuGroupName.of("디저트");

        // then
        assertThat(name1).isEqualTo(name2);
        assertThat(name1.hashCode()).isEqualTo(name2.hashCode());
    }

    @Test
    @DisplayName("다른 값을 가진 MenuGroupName 객체는 equals 비교에서 false를 반환한다")
    void notEqualMenuGroupNames() {
        // given
        MenuGroupName name1 = MenuGroupName.of("디저트");
        MenuGroupName name2 = MenuGroupName.of("메인 요리");

        // then
        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    @DisplayName("toString 메서드는 이름 값을 반환한다")
    void toStringReturnsNameValue() {
        // given
        String name = "음료수";
        MenuGroupName menuGroupName = MenuGroupName.of(name);

        // then
        assertThat(menuGroupName.toString()).isEqualTo(name);
    }
}
