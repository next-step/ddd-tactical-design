package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import kitchenpos.product.tobe.domain.ProductPrice
import kitchenpos.product.tobe.domain.ProductPricePolicy
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuTest {

    @Test
    @DisplayName("Menu를 생성한다")
    fun create() {
        // given
        val 추천메뉴 = Fixtures.menuGroup(name = "추천메뉴")

        val 양념치킨 = Fixtures.product(name = "양념치킨", price = 16000)
        val 후라이드치킨 = Fixtures.product(name = "후라이드치킨", price = 17000)

        // when
        val menu = Fixtures.menu(
            name = "후라이드+양념치킨",
            price = 33000,
            display = MenuDisplay.DISPLAYED,
            menuGroup = 추천메뉴,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(product = 양념치킨, quantity = 1),
                    Fixtures.menuProduct(product = 후라이드치킨, quantity = 1)
                )
            )
        )

        // then
        assertThat(menu.menuName.name).isEqualTo("후라이드+양념치킨")
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(33000))
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
        assertThat(menu.menuGroup).isEqualTo(추천메뉴)
        assertThat(menu.menuProducts.menuProducts).hasSize(2)
    }

    @Test
    @DisplayName("Menu의 가격을 변경한다")
    fun changeMenuPrice() {
        // given
        val product = Fixtures.product(name = "후라이드", price = 16000)
        val menu = Fixtures.menu(
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(product = product, quantity = 2)
                )
            )
        )

        // when
        menu.changePrice(MenuPrice(BigDecimal.valueOf(31000)))

        // then
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(31000))
    }

    @Test
    @DisplayName("Menu의 가격을 변경할 때 Menu Price > Menu Amount이면 변경할 수 없다")
    fun changeMenuPriceFail() {
        // given
        val product = Fixtures.product(name = "후라이드", price = 16000)
        val menu = Fixtures.menu(
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(product = product, quantity = 2)
                )
            )
        )

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menu.changePrice(MenuPrice(BigDecimal.valueOf(33000)))
        }
    }

    @Test
    @DisplayName("Menu를 Display한다")
    fun displayMenu() {
        // given
        val product = Fixtures.product(name = "후라이드", price = 16000)
        val menu = Fixtures.menu(
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.NOT_DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(product = product, quantity = 2)
                )
            )
        )

        // when
        menu.display()

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
    }

    @Test
    @DisplayName("Menu가 Menu Price > Menu Amount이면 Display할 수 없다")
    fun displayMenuFail() {
        // given
        val product = Fixtures.product(name = "후라이드", price = 16000)
        val menu = Fixtures.menu(
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.NOT_DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(product = product, quantity = 2)
                )
            )
        )
        // given 상품가격을 낮춘다
        product.changePrice(ProductPrice(ProductPricePolicy(), 15000.toBigDecimal()))

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menu.display()
        }
    }

    @Test
    @DisplayName("Menu를 Not Display한다")
    fun notDisplayMenu() {
        // given
        val product = Fixtures.product(name = "후라이드", price = 16000)
        val menu = Fixtures.menu(
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(product = product, quantity = 2)
                )
            )
        )

        // when
        menu.notDisplay()

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED)
    }
}
