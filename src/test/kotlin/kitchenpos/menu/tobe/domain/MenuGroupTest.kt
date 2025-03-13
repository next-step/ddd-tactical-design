package kitchenpos.menu.tobe.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource


class MenuGroupTest {
    @Test
    @DisplayName("MenuGroup을 생성한다.")
    fun create() {
        val menuGroup = MenuGroup(name = "추천 메뉴")

        assertThat(menuGroup.name).isEqualTo(menuGroup.name)
    }

    @EmptySource
    @ParameterizedTest
    @DisplayName("MenuGroup의 이름값은 필수값이다.")
    fun createNameFail(name: String) {
        assertThatIllegalArgumentException().isThrownBy {
            MenuGroup(name = name)
        }
    }
}
