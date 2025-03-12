package kitchenpos.menu.tobe.application

import java.math.BigDecimal
import kitchenpos.menu.tobe.application.dto.CreateMenuProductReq
import kitchenpos.menu.tobe.application.dto.CreateMenuReq
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import kitchenpos.menu.tobe.domain.MenuNamePolicy
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.domain.ProductClient
import kitchenpos.menu.tobe.infra.DefaultProductClient
import kitchenpos.menu.tobe.infra.FakeMenuGroupRepository
import kitchenpos.menu.tobe.infra.FakeMenuRepository
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.product.tobe.infra.FakeProductRepository
import kitchenpos.product.tobe.infra.FakeProfanities
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class MenuServiceTest {
    private lateinit var menuService: MenuService

    private lateinit var menuRepository: MenuRepository
    private lateinit var menuGroupRepository: MenuGroupRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var productClient: ProductClient
    private lateinit var menuAmountService: MenuAmountService

    private lateinit var menuGroup: MenuGroup
    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        menuRepository = FakeMenuRepository()
        menuGroupRepository = FakeMenuGroupRepository()
        productRepository = FakeProductRepository()
        productClient = DefaultProductClient(productRepository)
        menuAmountService = DefaultMenuAmountService(productClient)
        menuService =
            MenuService(
                menuRepository,
                menuGroupRepository,
                productRepository,
                MenuNamePolicy(FakeProfanities()),
                menuAmountService
            )

        menuGroup = menuGroupRepository.save(Fixtures.menuGroup(name = "추천메뉴"))
        product = productRepository.save(Fixtures.product(name = "후라이드", price = 16_000))
    }

    @DisplayName("Menu를 등록한다")
    @Test
    fun create() {
        // given
        val request = CreateMenuReq(
            menuGroupId = menuGroup.id!!,
            name = "후라이드1마리",
            price = 16_000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = listOf(
                CreateMenuProductReq(
                    seq = 1,
                    productId = product.id!!,
                    quantity = 1
                )
            )
        )

        // when
        val menuResp = menuService.create(request)

        // then
        assertThat(menuResp).isNotNull
        assertAll(
            { assertThat(menuResp.id).isNotNull() },
            { assertThat(menuResp.name).isEqualTo("후라이드1마리") },
            { assertThat(menuResp.price).isEqualTo(BigDecimal.valueOf(16000)) },
            { assertThat(menuResp.display).isEqualTo(MenuDisplay.DISPLAYED) },
            { assertThat(menuResp.menuGroup.id).isEqualTo(menuGroup.id) },
            { assertThat(menuResp.menuProducts).hasSize(1) }
        )
    }

    @DisplayName("Menu를 등록할 때 MenuProduct의 Product가 존재하지 않는다")
    @Test
    fun createWithInvalidProducts() {
        // given
        val request = CreateMenuReq(
            menuGroupId = menuGroup.id!!,
            name = "후라이드1마리",
            price = 16_000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = listOf(
                CreateMenuProductReq(
                    seq = 1,
                    productId = product.id!!,
                    quantity = 1
                ),
                CreateMenuProductReq(
                    seq = 2,
                    productId = Fixtures.INVALID_UUID,
                    quantity = 1
                )
            )
        )

        // when then
        assertThatThrownBy { menuService.create(request) }
            .isInstanceOf(NoSuchElementException::class.java)
    }

    @DisplayName("Menu를 등록할 때 MenuGroup이 존재하지 않는다")
    @Test
    fun createWithInvalidMenuGroup() {
        // given
        val request = CreateMenuReq(
            menuGroupId = Fixtures.INVALID_UUID,
            name = "후라이드1마리",
            price = 16_000,
            display = MenuDisplay.DISPLAYED,
            menuProducts = listOf(
                CreateMenuProductReq(
                    seq = 1,
                    productId = product.id!!,
                    quantity = 1
                )
            )
        )

        // when then
        assertThatThrownBy { menuService.create(request) }
            .isInstanceOf(NoSuchElementException::class.java)
    }


    @DisplayName("Menu를 등록하려면 MenuPrice <= MenuAmount를 만족해야한다")
    @Test
    fun createWithInvalidPrice() {
        // given
        val request = CreateMenuReq(
            menuGroupId = menuGroup.id!!,
            name = "후라이드2마리",
            price = 32_001,
            display = MenuDisplay.DISPLAYED,
            menuProducts = listOf(
                CreateMenuProductReq(
                    seq = 1,
                    productId = product.id!!,
                    quantity = 2
                )
            )
        )

        // when then
        assertThatIllegalArgumentException().isThrownBy { menuService.create(request) }
    }

    @DisplayName("MenuPrice를 변경한다")
    @Test
    fun changePrice() {
        // given
        val menu = menuRepository.save(
            Fixtures.menu(
                menuAmountService = menuAmountService,
                name = "후라이드2마리",
                price = 32_000,
                display = MenuDisplay.DISPLAYED,
                menuGroup = menuGroup,
                menuProducts = MenuProducts(
                    listOf(Fixtures.menuProduct(productId = product.id!!, quantity = 2))
                ),
            )
        )
        val request = ChangeProductPriceReq(
            price = BigDecimal.valueOf(31_000)
        )

        // when
        menuService.changePrice(menu.id!!, request)

        // then
        assertThat(menu.menuPrice.price).isEqualTo(BigDecimal.valueOf(31_000))
    }

    @DisplayName("존재하지 않는 Menu는 MenuPrice를 변경할 수 없다")
    @Test
    fun changePriceInvalidMenuId() {
        // given
        val request = ChangeProductPriceReq(
            price = BigDecimal.valueOf(31_000)
        )

        // when then
        assertThatThrownBy {
            menuService.changePrice(
                Fixtures.INVALID_UUID,
                request
            )
        }.isInstanceOf(NoSuchElementException::class.java)
    }

    @DisplayName("MenuPrice를 변경할 때 MenuPrice <= MenuAmount를 만족해야한다")
    @Test
    fun changePriceLessThanAmount() {
        // given
        val menu = menuRepository.save(
            Fixtures.menu(
                menuAmountService = menuAmountService,
                name = "후라이드2마리",
                price = 32_000,
                display = MenuDisplay.DISPLAYED,
                menuGroup = menuGroup,
                menuProducts = MenuProducts(
                    listOf(Fixtures.menuProduct(productId = product.id!!, quantity = 2))
                ),
            )
        )
        val request = ChangeProductPriceReq(
            price = BigDecimal.valueOf(33_000)
        )

        // when then
        assertThatIllegalArgumentException().isThrownBy {
            menuService.changePrice(menu.id!!, request)
        }
    }

    @DisplayName("Menu를 Display한다")
    @Test
    fun menuDisplay() {
        // given
        val menu = menuRepository.save(
            Fixtures.menu(
                menuAmountService = menuAmountService,
                name = "후라이드2마리",
                price = 32_000,
                display = MenuDisplay.NOT_DISPLAYED,
                menuGroup = menuGroup,
                menuProducts = MenuProducts(
                    listOf(Fixtures.menuProduct(productId = product.id!!, quantity = 2))
                ),
            )
        )

        // when
        menuService.display(menu.id!!)

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.DISPLAYED)
    }

    @DisplayName("존재하지 않는 Menu는 Displayed할 수 없다")
    @Test
    fun displayInvalidMenuId() {
        // when then
        assertThatThrownBy {
            menuService.display(Fixtures.INVALID_UUID)
        }.isInstanceOf(NoSuchElementException::class.java)
    }

    @DisplayName("Menu를 Not Displayed 한다")
    @Test
    fun menuNotDisplay() {
        // given
        val menu = menuRepository.save(
            Fixtures.menu(
                menuAmountService = menuAmountService,
                name = "후라이드2마리",
                price = 32_000,
                display = MenuDisplay.DISPLAYED,
                menuGroup = menuGroup,
                menuProducts = MenuProducts(
                    listOf(Fixtures.menuProduct(productId = product.id!!, quantity = 2))
                ),
            )
        )

        // when
        menuService.notDisplay(menu.id!!)

        // then
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED)
    }

    @DisplayName("존재하지 않는 Menu는 Not Displayed 할 수 없다")
    @Test
    fun notDisplayInvalidMenuId() {
        // when then
        assertThatThrownBy {
            menuService.notDisplay(Fixtures.INVALID_UUID)
        }.isInstanceOf(NoSuchElementException::class.java)
    }

}
