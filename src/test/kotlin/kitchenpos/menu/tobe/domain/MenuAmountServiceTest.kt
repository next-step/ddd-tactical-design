package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import kitchenpos.menu.tobe.infra.DefaultMenuAmountService
import kitchenpos.product.tobe.domain.ProductRepository
import kitchenpos.product.tobe.infra.FakeProductRepository
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuAmountServiceTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var menuAmountService: MenuAmountService

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepository()
        menuAmountService = DefaultMenuAmountService(productRepository)
    }


    @Test
    @DisplayName("Menu Amount를 계산한다")
    fun amount() {
        // given
        val productId1 = productRepository.save(Fixtures.product(name = "후라이드치킨", price = 17000)).id
        val productId2 = productRepository.save(Fixtures.product(name = "양념치킨", price = 16000)).id

        val menuProducts = MenuProducts(
            listOf(
                Fixtures.menuProduct(productId = productId1!!, quantity = 2),
                Fixtures.menuProduct(productId = productId2!!, quantity = 1),
            )
        )

        // when
        val amount = menuProducts.amount(menuAmountService)

        // then
        assertThat(amount).isEqualTo(BigDecimal.valueOf(50000))
    }
}

