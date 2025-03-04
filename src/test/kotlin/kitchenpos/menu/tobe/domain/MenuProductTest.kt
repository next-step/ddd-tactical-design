package kitchenpos.menu.tobe.domain

import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuProductTest {

    @Test
    @DisplayName("MenuProduct의 quantity는 0개 이상이어야 한다")
    fun menuProductQuantity() {
        val product = Fixtures.product(name = "후라이드치킨", price = 17000)

        assertThatIllegalArgumentException().isThrownBy {
            MenuProduct(product = product, quantity = -1)
        }
    }

}
