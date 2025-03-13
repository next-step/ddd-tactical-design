package kitchenpos.menu.tobe.domain

import java.util.*
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuProductTest {

    @Test
    @DisplayName("MenuProduct의 quantity는 0개 이상이어야 한다")
    fun menuProductQuantity() {
        assertThatIllegalArgumentException().isThrownBy {
            MenuProduct(productId = UUID.randomUUID(), quantity = -1)
        }
    }

}
