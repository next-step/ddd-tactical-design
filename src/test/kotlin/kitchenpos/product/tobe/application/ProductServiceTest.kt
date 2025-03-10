package kitchenpos.product.tobe.application

import java.math.BigDecimal
import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.domain.MenuProductPriceChanged
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.domain.ProductClient
import kitchenpos.menu.tobe.infra.DefaultMenuAmountService
import kitchenpos.menu.tobe.infra.DefaultMenuProductPriceChanged
import kitchenpos.menu.tobe.infra.DefaultProductClient
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
    private lateinit var productClient: ProductClient
    private lateinit var menuRepository: MenuRepository
    private lateinit var profanities: Profanities
    private lateinit var productService: ProductService
    private lateinit var menuProductPriceChanged: MenuProductPriceChanged

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepository()
        productClient = DefaultProductClient(productRepository)
        menuRepository = FakeMenuRepository()
        profanities = FakeProfanities()
        menuProductPriceChanged =
            DefaultMenuProductPriceChanged(menuRepository, DefaultMenuAmountService(productClient = productClient))
        productService = ProductService(productRepository, profanities, menuProductPriceChanged)
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
        productService.changePrice(product.id, ChangeProductPriceReq(BigDecimal.valueOf(17000)))

        // then
        assertThat(product.productPrice.price).isEqualTo(BigDecimal.valueOf(17000))
    }

    @Test
    @DisplayName("등록되지 않는 `Product`의 `price`는 변경할 수 없다")
    fun changePriceFail() {
        // when then
        Assertions.assertThatThrownBy {
            productService.changePrice(Fixtures.INVALID_UUID, ChangeProductPriceReq(BigDecimal.valueOf(17000)))
        }.isInstanceOf(NoSuchElementException::class.java)
    }
}
