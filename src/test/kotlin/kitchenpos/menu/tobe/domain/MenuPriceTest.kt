package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuPriceTest {

    @Test
    @DisplayName("MenuPrice를 생성한다")
    fun createMenuPrice() {
        val price = BigDecimal.valueOf(1000L)

        val menuPrice = MenuPrice(price)

        assertEquals(price, menuPrice.price)
    }

    @Test
    @DisplayName("MenuPrice는 0원 이상이어야 한다")
    fun createMenuPriceFail() {
        assertThrows(IllegalArgumentException::class.java) {
            MenuPrice(BigDecimal.valueOf(-1))
        }
    }
}
