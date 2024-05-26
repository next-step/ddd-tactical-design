package kitchenpos.products.application.tobe

import kitchenpos.fixture.ProductFixtures
import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductPrice
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class ProductPriceTest {
    @Test
    fun `상품의 가격은 0보다 크거나 같아야한다`() {
        Assertions.assertThatThrownBy {
            Product(
                displayedName = "양념감자",
                price = ProductPrice(BigDecimal.valueOf(-1000L))
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("상품의 가격은 0보다 작을 수 없습니다")
    }

    @Test
    @ValueSource(longs = [0L, 1L, 1000L])
    @ParameterizedTest
    fun `정상적인 상품 가격 변경`() {
        val product = ProductFixtures.product()

        product.changePrice(ProductPrice(BigDecimal.valueOf(5000L)))

        val `5천원` = ProductPrice(BigDecimal.valueOf(5000L))

        Assertions.assertThat(product.price).isEqualTo(`5천원`)
    }
}
