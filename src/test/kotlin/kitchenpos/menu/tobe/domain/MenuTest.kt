package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import java.util.*
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuTest {
    private lateinit var menuGroup: MenuGroup
    private lateinit var productId1: UUID
    private lateinit var productId2: UUID


    @BeforeEach
    fun setUp() {
        menuGroup = Fixtures.menuGroup(name = "추천메뉴")
        productId1 = UUID.randomUUID()
        productId2 = UUID.randomUUID()
    }


    @Test
    @DisplayName("Menu를 생성한다")
    fun create() {
        // when
        val menu = Fixtures.menu(
            menuAmountService = { BigDecimal.valueOf(33000) },
            name = "후라이드+양념치킨",
            price = 33000,
            display = MenuDisplay.DISPLAYED,
            menuGroup = menuGroup,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = productId1, quantity = 1),
                    Fixtures.menuProduct(productId = productId2, quantity = 1)
                )
            )
        )

        // then
        assertThat(menu.menuName.name).isEqualTo("후라이드+양념치킨")
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(33000))
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
        assertThat(menu.menuGroup).isEqualTo(menuGroup)
        assertThat(menu.menuProducts.menuProducts).hasSize(2)
    }

    @Test
    @DisplayName("Menu의 가격을 변경한다")
    fun changeMenuPrice() {
        // given
        val menuAmount = BigDecimal.valueOf(32000)
        val menu = Fixtures.menu(
            menuAmountService = { menuAmount },
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = productId1, quantity = 2)
                )
            )
        )

        // when
        menu.changePrice({ menuAmount }, MenuPrice(BigDecimal.valueOf(31000)))

        // then
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(31000))
    }

    @Test
    @DisplayName("Menu의 가격을 변경할 때 Menu Price > Menu Amount이면 변경할 수 없다")
    fun changeMenuPriceFail() {
        // given
        val menuAmount = BigDecimal.valueOf(32000)
        val menu = Fixtures.menu(
            menuAmountService = { menuAmount },
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = productId1, quantity = 2)
                )
            )
        )

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menu.changePrice({ menuAmount }, MenuPrice(BigDecimal.valueOf(33000)))
        }
    }

    @Test
    @DisplayName("Menu를 Display한다")
    fun displayMenu() {
        // given
        val menuAmount = BigDecimal.valueOf(32000)
        val menu = Fixtures.menu(
            menuAmountService = { menuAmount },
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.NOT_DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = productId1, quantity = 2)
                )
            )
        )

        // when
        menu.display({ menuAmount })

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
    }

    @Test
    @DisplayName("Menu가 Menu Price > Menu Amount이면 Display할 수 없다")
    fun displayMenuFail() {
        // given
        val menu = Fixtures.menu(
            menuAmountService = { BigDecimal.valueOf(32000) },
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.NOT_DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = productId1, quantity = 2)
                )
            )
        )

        // given 변경된 메뉴금액
        val menuAmount = BigDecimal.valueOf(31000)

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menu.display({ menuAmount })
        }
    }

    @Test
    @DisplayName("Menu를 Not Display한다")
    fun notDisplayMenu() {
        // given
        val menu = Fixtures.menu(
            menuAmountService = { BigDecimal.valueOf(32000) },
            name = "후라이드2마리",
            price = 32000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = MenuProducts(
                listOf(
                    Fixtures.menuProduct(productId = productId1, quantity = 2)
                )
            )
        )

        // when
        menu.notDisplay()

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED)
    }
}
