package kitchenpos.menu.tobe.domain

import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MenuTest {
    @Test
    @DisplayName("메뉴 금액 계산")
    fun amount() {
        val 양념치킨 = Fixtures.product(name="양념치킨", price=16000)
        val 후라이드치킨 = Fixtures.product(name="후라이드치킨", price=17000)
        val menu = Fixtures.menu(
            name = "후라이드양념치킨",
            price = 0,
            displayed = true,
            menuProducts = listOf(
                Fixtures.menuProduct(product = 양념치킨, quantity = 1),
                Fixtures.menuProduct(product = 후라이드치킨, quantity = 2)
            )
        )

        val amount = menu.amount()

        assertThat(amount).isEqualTo(BigDecimal.valueOf(50000))
    }
}
