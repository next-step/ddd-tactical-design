package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuProductsTest {
    @Test
    @DisplayName("Menu amount를 계산한다")
    fun amount() {
        // given
        val 후라이드치킨 = Fixtures.product(name = "후라이드치킨", price = 17000)
        val 양념치킨 = Fixtures.product(name = "양념치킨", price = 16000)
        val menuProducts = MenuProducts(
            listOf(
                Fixtures.menuProduct(product = 후라이드치킨, quantity = 2),
                Fixtures.menuProduct(product = 양념치킨, quantity = 1),
            )
        )

        // when
        val amount = menuProducts.amount()

        // then
        assertThat(amount).isEqualTo(BigDecimal.valueOf(50000))
    }

}

