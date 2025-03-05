package kitchenpos.product.tobe.application

import java.math.BigDecimal
import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.infra.DefaultMenuAmountService
import kitchenpos.menu.tobe.infra.FakeMenuRepository
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.product.tobe.infra.FakeProductRepository
import kitchenpos.product.tobe.infra.FakeProfanities
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductServiceTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var menuRepository: MenuRepository
    private lateinit var profanities: Profanities
    private lateinit var productService: ProductService
    private lateinit var menuAmountService: MenuAmountService

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepository()
        menuRepository = FakeMenuRepository()
        profanities = FakeProfanities()
        menuAmountService = DefaultMenuAmountService(productRepository)
        productService = ProductService(productRepository, menuRepository, profanities, menuAmountService)
    }

    @Test
    @DisplayName("`Product`를 등록한다")
    fun createProduct() {
        // given
        val request = CreateProductReq("양념치킨", BigDecimal.valueOf(16000))

        // when
        val product = productService.create(request)

        // then
        assertThat(product.name).isEqualTo("양념치킨")
        assertThat(product.price).isEqualTo(BigDecimal.valueOf(16000))
    }

    @Test
    @DisplayName("`Product`를 전체 조회한다")
    fun findAll() {
        // given
        val request1 = CreateProductReq("양념치킨", BigDecimal.valueOf(16000))
        val request2 = CreateProductReq("후라이드치킨", BigDecimal.valueOf(17000))
        productService.create(request1)
        productService.create(request2)

        // when
        val products = productService.findAll()

        // then
        assertThat(products.size).isEqualTo(2)
    }

    @Test
    @DisplayName("`Product`의 `price`를 변경한다")
    fun changePrice() {
        // given
        val product = productRepository.save(Fixtures.product(name = "양념치킨", price = 16000))

        // when
        val changedProduct = productService.changePrice(product.id!!, ChangeProductPriceReq(BigDecimal.valueOf(17000)))

        // then
        assertThat(changedProduct.price).isEqualTo(BigDecimal.valueOf(17000))
    }

    @Test
    @DisplayName("등록되지 않는 `Product`의 `price`는 변경할 수 없다")
    fun changePriceFail() {
        // when then
        Assertions.assertThatThrownBy {
            productService.changePrice(Fixtures.INVALID_UUID, ChangeProductPriceReq(BigDecimal.valueOf(17000)))
        }.isInstanceOf(NoSuchElementException::class.java)
    }

    @Test
    @DisplayName("`Product`의 `price`를 변경 할 때 `Product`를 포함한 `Menu`들 중 `Menu Price > Menu Amount`인 `Menu`는 `Not Displayed`된다")
    fun changePriceMenuNotDisplayed() {
        // given
        val product = productRepository.save(Fixtures.product(name = "양념치킨", price = 16000))
        val menu = menuRepository.save(
            Fixtures.menu(
                menuAmountService = { BigDecimal.valueOf(32000) },
                name = "양념치킨 세트",
                price = 32000,
                display = MenuDisplay.DISPLAYED,
                menuProducts = MenuProducts(listOf(Fixtures.menuProduct(productId = product.id!!, quantity = 2)))
            )
        )

        // when
        val changedProduct = productService.changePrice(product.id!!, ChangeProductPriceReq(BigDecimal.valueOf(15000)))

        // then
        assertThat(changedProduct.price).isEqualTo(BigDecimal.valueOf(15000))
        assertThat(menu.menuDisplay).isEqualTo(MenuDisplay.NOT_DISPLAYED)
    }
}
