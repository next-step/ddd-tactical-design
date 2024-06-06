package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.domain.Price
import kitchenpos.products.tobe.domain.TobeProduct
import kitchenpos.products.tobe.domain.TobeProductRepository
import kitchenpos.products.tobe.infra.InMemoryTobeProductRepository
import kitchenpos.share.domain.FakeProfanities
import kitchenpos.shared.domain.Profanities
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

class TobeProductServiceTest {
    private lateinit var tobeProductRepository: TobeProductRepository
    private lateinit var sut: TobeProductService

    @BeforeEach
    fun setUp() {
        tobeProductRepository = InMemoryTobeProductRepository()
        sut = TobeProductService(tobeProductRepository = tobeProductRepository)
    }

    @DisplayName("상품 등록")
    @Test
    fun case_1() {
        // given
        val request = createProduct()

        // when
        val savedProduct = sut.create(request)

        // then
        assertThat(savedProduct).isNotNull()
    }

    @DisplayName("상품 가격 변경")
    @Test
    fun case_2() {
        // given
        val savedProduct = tobeProductRepository.save(createProduct())
        val price = 15_000
        val productId = savedProduct.id
        val changePriceRequest = createProduct(price = price)

        // when
        val changedProduct = sut.changePrice(productId, changePriceRequest)

        // then
        assertThat(changedProduct).isNotNull()
        assertThat(changedProduct.id).isEqualTo(productId)
        assertThat(changedProduct.price).isEqualTo(Price.of(price))
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    fun case_3() {
        // given
        tobeProductRepository.save(createProduct(name = "후라이드"))
        tobeProductRepository.save(createProduct(name = "양념"))

        // when
        val products = sut.findAll()

        // then
        assertThat(products).isNotEmpty
        assertThat(products.size).isEqualTo(2)
    }

    private fun createProduct(
        id: UUID = UUID.randomUUID(),
        name: String = "후라이드",
        price: Int = 10_000,
        profanities: Profanities = FakeProfanities("비속어"),
    ): TobeProduct {
        return TobeProduct(id, name, price, profanities)
    }
}
