package kitchenpos.menu.tobe.domain

import kitchenpos.product.tobe.infra.FakeProfanities
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EmptySource

class MenuNameTest {
    @Test
    @DisplayName("MenuName을 생성한다")
    fun create() {
        val name = "양념치킨"
        val menuName = MenuName(menuNamePolicy = MenuNamePolicy(FakeProfanities()), name = name)

        assertEquals(name, menuName.name)
    }

    @ParameterizedTest
    @DisplayName("MenuName은 필수값이다")
    @EmptySource
    fun createFail(name: String) {
        assertThatIllegalArgumentException().isThrownBy {
            MenuName(menuNamePolicy = MenuNamePolicy(FakeProfanities()), name = name)
        }
    }

    @DisplayName("Profanities의 Profanity가 포함된 MenuName은 생성할 수 없다")
    @ParameterizedTest
    @CsvSource(value = ["욕설메뉴", "비속어"])
    fun createProfanityFail(name: String) {
        assertThatIllegalArgumentException().isThrownBy {
            MenuName(menuNamePolicy = MenuNamePolicy(FakeProfanities(listOf("욕설", "비속어"))), name = name)
        }
    }
}
