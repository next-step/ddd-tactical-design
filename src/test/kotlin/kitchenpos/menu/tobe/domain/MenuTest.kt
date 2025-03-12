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
    private lateinit var productClient: ProductClient

    private val menuGroup: MenuGroup = Fixtures.menuGroup(name = "추천메뉴")
    private val productId1: UUID = UUID.randomUUID()
    private val productId2: UUID = UUID.randomUUID()


    @BeforeEach
    fun setUp() {
        productClient = object : ProductClient {
            override fun getProductPrice(productId: UUID): BigDecimal {
                return when (productId) {
                    productId1 -> BigDecimal.valueOf(16000)
                    productId2 -> BigDecimal.valueOf(17000)
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }


    @Test
    @DisplayName("Menu를 생성한다")
    fun create() {
        // when
        val menu = Fixtures.menu(
            productClient = productClient,
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
    @DisplayName("MenuPrice를 변경한다")
    fun changeMenuPrice() {
        // given
        val menuAmount = BigDecimal.valueOf(32000)
        val menu = Fixtures.menu(
            productClient = productClient,
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
        menu.changePrice(productClient, MenuPrice(BigDecimal.valueOf(31000)))

        // then
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(31000))
    }

    @Test
    @DisplayName("MenuPrice를 변경할 때 MenuPrice > MenuAmount이면 변경할 수 없다")
    fun changeMenuPriceFail() {
        // given
        val menuAmount = BigDecimal.valueOf(32000)
        val menu = Fixtures.menu(
            productClient = productClient,
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
            menu.changePrice(productClient, MenuPrice(BigDecimal.valueOf(33000)))
        }
    }

    @Test
    @DisplayName("Menu를 Display한다")
    fun displayMenu() {
        // given
        val menu = Fixtures.menu(
            productClient = productClient,
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
        menu.display(productClient)

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
    }

    @Test
    @DisplayName("Menu가 Menu Price > Menu Amount이면 Display할 수 없다")
    fun displayMenuFail() {
        // given
        val menu = Fixtures.menu(
            productClient = productClient,
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
        productClient = object : ProductClient {
            override fun getProductPrice(productId: UUID): BigDecimal {
                return when (productId) {
                    productId1 -> BigDecimal.valueOf(15000)
                    else -> throw IllegalArgumentException()
                }
            }
        }

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menu.display(productClient)
        }
    }

    @Test
    @DisplayName("Menu를 Not Display한다")
    fun notDisplayMenu() {
        // given
        val menu = Fixtures.menu(
            productClient = productClient,
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
