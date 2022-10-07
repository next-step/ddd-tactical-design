package kitchenpos.menus.menugroup.tobe.domain;

import kitchenpos.common.domain.vo.exception.InvalidNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupTest {

    @DisplayName("메뉴그룹을 생성한다.")
    @Nested
    class CreateTest {

        @DisplayName("성공")
        @Test
        void success() {
            final MenuGroup menuGroup = MenuGroup.create("인기메뉴");

            assertAll(
                    () -> assertThat(menuGroup.id()).isNotNull(),
                    () -> assertThat(menuGroup.name().value()).isEqualTo("인기메뉴")
            );
        }

        @DisplayName("메뉴그룹명이 있어야 한다.")
        @Test
        void error() {
            assertThatThrownBy(() -> MenuGroup.create(null))
                    .isInstanceOf(InvalidNameException.class);
        }
    }
}
