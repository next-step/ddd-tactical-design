package kitchenpos.product.tobe.application

import kitchenpos.common.domain.Profanities
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.product.tobe.infra.FakeProductRepository
import kitchenpos.product.tobe.infra.FakeProfanities
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductServiceTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var profanities: Profanities
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepository()
        profanities = FakeProfanities()
        productService = ProductService(productRepository, profanities)
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


}
