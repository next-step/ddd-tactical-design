package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.tobe.Profanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuNameTest {

    private final Profanities profanities = text -> text.equals("바보"); // 테스트용 비속어 설정

    @Test
    @DisplayName("MenuName 객체를 생성할 수 있다")
    void create() {
        // when
        MenuName menuName = MenuName.of("맛있는 메뉴", profanities);

        // then
        assertThat(menuName.getName()).isEqualTo("맛있는 메뉴");
    }

    @Test
    @DisplayName("메뉴 이름이 null이면 예외가 발생한다")
    void createWithNullName() {
        // when & then
        assertThatThrownBy(() -> MenuName.of(null, profanities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 이름은 필수입니다");
    }

    @Test
    @DisplayName("메뉴 이름이 빈 문자열이면 예외가 발생한다")
    void createWithEmptyName() {
        // when & then
        assertThatThrownBy(() -> MenuName.of("", profanities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 이름은 필수입니다");
    }

    @Test
    @DisplayName("메뉴 이름이 공백 문자열이면 예외가 발생한다")
    void createWithBlankName() {
        // when & then
        assertThatThrownBy(() -> MenuName.of("   ", profanities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("메뉴 이름은 필수입니다");
    }

    @Test
    @DisplayName("메뉴 이름에 비속어가 포함되면 예외가 발생한다")
    void createWithProfanity() {
        // when & then
        assertThatThrownBy(() -> MenuName.of("바보", profanities))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비속어가 포함되어 있습니다");
    }

    @Test
    @DisplayName("MenuName 객체끼리 동등성 비교가 가능하다")
    void equals() {
        // given
        MenuName menuName1 = MenuName.of("맛있는 메뉴", profanities);
        MenuName menuName2 = MenuName.of("맛있는 메뉴", profanities);
        MenuName menuName3 = MenuName.of("다른 메뉴", profanities);

        // then
        assertThat(menuName1).isEqualTo(menuName2);
        assertThat(menuName1).isNotEqualTo(menuName3);
    }
}