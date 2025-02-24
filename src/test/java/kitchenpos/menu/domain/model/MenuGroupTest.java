package kitchenpos.menu.domain.model;

import kitchenpos.menu.domain.exception.MenuGroupNameValidationException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupTest {
    private static final UUID ID = UUID.randomUUID();
    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String NAME = "테스트 메뉴 그룹";

    @DisplayName("메뉴 그룹을 생성할 수 있다")
    @Test
    void create() {
        // when
        MenuGroup menuGroup = MenuGroup.create(ID, NAME, name -> false);

        // then
        assertAll(
                () -> assertThat(menuGroup).isNotNull(),
                () -> assertThat(menuGroup.getId()).isEqualTo(ID),
                () -> assertThat(menuGroup.getName()).isEqualTo(NAME)
        );
    }

    @DisplayName("MenuGroupName은 공백이나 빈값이 올 수 없다")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {EMPTY, BLANK})
    void createWithEmptyName(String name) {
        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> MenuGroup.create(ID, name, nm -> false);

        // then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(MenuGroupNameValidationException.class)
                .hasMessage("메뉴 그룹 이름을 입력하세요");
    }

    @DisplayName("ID에 Null이 올 수 없다")
    @Test
    void createWithNullId() {
        // when
        ThrowableAssert.ThrowingCallable throwingCallable = () -> MenuGroup.create(null, NAME, nm -> false);

        // then
        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 그룹 ID가 Null 입니다.");
    }

}