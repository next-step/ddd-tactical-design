package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.domain.TobeProduct
import kitchenpos.products.tobe.domain.TobeProductRepository
import kitchenpos.products.tobe.infra.InMemoryTobeProductRepository
import kitchenpos.share.domain.FakeProfanities
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

    @DisplayName("save product to DB")
    @Test
    fun case_1() {
        // given
        val id = UUID.randomUUID()
        val name = "후라이드"
        val price = 10_000
        val profanities = FakeProfanities("비속어")
        val request = TobeProduct(id, name, price, profanities)

        // when
        val savedProduct = sut.create(request)

        // then
        assertThat(savedProduct).isNotNull()
    }
}
