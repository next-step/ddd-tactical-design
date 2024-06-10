package kitchenpos.menus.tobe.domain.menu

import kitchenpos.products.tobe.domain.TobeProduct
import kitchenpos.products.tobe.infra.InMemoryTobeProductRepository
import kitchenpos.share.domain.FakeProfanities
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import java.util.UUID.randomUUID

@ExtendWith(MockitoExtension::class)
class DefaultTobeProductClientTest {
    @Mock
    private lateinit var productRepository: InMemoryTobeProductRepository
    private lateinit var sut: DefaultTobeProductClient

    @BeforeEach
    fun setUp() {
        this.sut = DefaultTobeProductClient(productRepository)
    }

    @DisplayName("productRepository is not null")
    @Test
    fun case_1() {
        assertThat(productRepository).isNotNull()
    }

    @DisplayName("menuProducts 의 모든 UUID 가 product 에 존재한다.")
    @Test
    fun case_2() {
        // given
        val profanities = FakeProfanities()
        val firstUUID = randomUUID()
        val secondUUID = randomUUID()
        val menuProductIds = listOf(firstUUID, secondUUID)
        val products =
            listOf(
                TobeProduct(firstUUID, "후라이드", 10_000, profanities),
                TobeProduct(secondUUID, "양념", 10_000, profanities),
            )
        given(productRepository.findAllByIdIn(any())).willReturn(products)

        // when
        val result = sut.validateAllProductsExists(menuProductIds)

        // then
        assertThat(result).isTrue()
    }

    @DisplayName("menuProducts 의 일부 UUID 만 product 에 존재한다.")
    @Test
    fun case_3() {
        // given
        val profanities = FakeProfanities()
        val firstUUID = randomUUID()
        val secondUUID = randomUUID()
        val menuProductIds = listOf(firstUUID, secondUUID)
        val products =
            listOf(
                TobeProduct(firstUUID, "후라이드", 10_000, profanities),
                TobeProduct(UUID.randomUUID(), "양념", 10_000, profanities),
            )
        given(productRepository.findAllByIdIn(any())).willReturn(products)

        // when
        val result = sut.validateAllProductsExists(menuProductIds)

        // then
        assertThat(result).isFalse()
    }
}
