package kitchenpos.product.tobe.application

import java.math.BigDecimal
import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.infra.FakeMenuRepository
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.product.tobe.infra.FakeProductRepository
import kitchenpos.product.tobe.infra.FakeProfanities
import kitchenpos.utils.Fixtures
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductServiceTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var menuRepository: MenuRepository
    private lateinit var profanities: Profanities
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepository()
        menuRepository = FakeMenuRepository()
        profanities = FakeProfanities()
        productService = ProductService(productRepository, menuRepository, profanities)
    }

    @Test
    @DisplayName("상품생성 / 성공")
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
    @DisplayName("상품목록조회 / 성공")
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
    @DisplayName("상품가격변경 / 성공")
    fun changePrice() {
        // given
        val product = productRepository.save(Fixtures.product(name = "양념치킨", price = 16000))

        // when
        val changedProduct = productService.changePrice(product.id!!, ChangeProductPriceReq(BigDecimal.valueOf(17000)))

        // then
        assertThat(changedProduct.price).isEqualTo(BigDecimal.valueOf(17000))
    }

    @Test
    @DisplayName("메뉴상품가격합 초과 / 상품가격변경 / 메뉴가 비노출된다")
    fun changePriceMenuNotDisplayed() {
        // given
        val product = productRepository.save(Fixtures.product(name = "양념치킨", price = 16000))
        val menu = menuRepository.save(
            Fixtures.menu(
                name = "양념치킨 세트",
                price = 32000,
                displayed = true,
                menuProducts = listOf(Fixtures.menuProduct(product, 2))
            )
        )

        // when
        val changedProduct = productService.changePrice(product.id!!, ChangeProductPriceReq(BigDecimal.valueOf(15000)))

        // then
        assertThat(changedProduct.price).isEqualTo(BigDecimal.valueOf(15000))
        assertThat(menu.displayed).isFalse()
    }
}
