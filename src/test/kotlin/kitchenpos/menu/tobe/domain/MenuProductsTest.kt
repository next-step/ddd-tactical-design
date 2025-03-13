package kitchenpos.menu.tobe.domain

import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuProductsTest {
    @Test
    @DisplayName("MenuProducts는 1개 이상이여야한다")
    fun menuProductsNotEmpty() {
        assertThatIllegalArgumentException().isThrownBy {
            MenuProducts(listOf())
        }
    }
}

