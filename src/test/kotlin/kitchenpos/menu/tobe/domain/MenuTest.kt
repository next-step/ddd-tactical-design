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
    private val menuGroup: MenuGroup = Fixtures.menuGroup(name = "추천메뉴")
    private val productId1: UUID = UUID.randomUUID()
    private val productId2: UUID = UUID.randomUUID()
    private lateinit var productInfos: Map<UUID, ProductInfo>
    private lateinit var menuProducts: MenuProducts


    @BeforeEach
    fun setUp() {
        productInfos = mapOf(
            productId1 to ProductInfo(productId1, BigDecimal.valueOf(10000)),
            productId2 to ProductInfo(productId2, BigDecimal.valueOf(15000)),
        )
        menuProducts = MenuProducts(
            listOf(
                Fixtures.menuProduct(productId = productId1, quantity = 2),
                Fixtures.menuProduct(productId = productId2, quantity = 1)
            )
        )
    }


    @Test
    @DisplayName("Menu를 생성한다")
    fun create() {
        // when
        val menu = Fixtures.menu(
            productInfos = productInfos,
            name = "후라이드2마리+양념치킨",
            price = 35000,
            display = MenuDisplay.DISPLAYED,
            menuGroup = menuGroup,
            menuProducts = menuProducts
        )

        // then
        assertThat(menu.menuName.name).isEqualTo("후라이드2마리+양념치킨")
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(35000))
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
        assertThat(menu.menuGroup).isEqualTo(menuGroup)
        assertThat(menu.menuProducts.menuProducts).hasSize(2)
    }

    @Test
    @DisplayName("MenuPrice를 변경한다")
    fun changeMenuPrice() {
        // given
        val menu = Fixtures.menu(
            productInfos = productInfos,
            price = 35000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = menuProducts
        )

        // when
        menu.changePrice(productInfos, MenuPrice(BigDecimal.valueOf(34000)))

        // then
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(34000))
    }

    @Test
    @DisplayName("MenuPrice를 변경할 때 MenuPrice > MenuAmount이면 변경할 수 없다")
    fun changeMenuPriceFail() {
        // given
        val menu = Fixtures.menu(
            productInfos = productInfos,
            price = 35000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = menuProducts,
        )

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menu.changePrice(productInfos, MenuPrice(BigDecimal.valueOf(36000)))
        }
    }

    @Test
    @DisplayName("Menu를 Display한다")
    fun displayMenu() {
        // given
        val menu = Fixtures.menu(
            productInfos = productInfos,
            price = 35000,
            display = MenuDisplay.NOT_DISPLAYED,
            menuProducts = menuProducts,
        )

        // when
        menu.display(productInfos)

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
    }

    @Test
    @DisplayName("Menu가 Menu Price > Menu Amount이면 Display할 수 없다")
    fun displayMenuFail() {
        // given
        val menu = Fixtures.menu(
            productInfos = productInfos,
            price = 35000,
            display = MenuDisplay.NOT_DISPLAYED,
            menuProducts = menuProducts,
        )
        // given product1 price 낮춤 (10000원 -> 5000)
        val changedProductInfos = mapOf(
            productId1 to ProductInfo(productId1, BigDecimal.valueOf(5000)),
            productId2 to ProductInfo(productId2, BigDecimal.valueOf(15000)),
        )

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menu.display(changedProductInfos)
        }
    }

    @Test
    @DisplayName("Menu를 Not Display한다")
    fun notDisplayMenu() {
        // given
        val menu = Fixtures.menu(
            productInfos = productInfos,
            price = 35000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = menuProducts,
        )

        // when
        menu.notDisplay()

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED)
    }
}
