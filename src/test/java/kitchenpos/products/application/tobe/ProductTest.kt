package kitchenpos.products.application.tobe

import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductPrice
import kitchenpos.tobe.Fixtures.product
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductTest {

    @Test
    fun `정상적인 상품 생성`() {
        val product = Product(
            displayedName = "양념감자",
            price = ProductPrice(BigDecimal.valueOf(4000))
        )

        val `4천원` = ProductPrice(BigDecimal.valueOf(4000))

        assertThat(product.displayedName).isEqualTo("양념감자")
        assertThat(product.price).isEqualTo(`4천원`)
    }

    @Test
    fun `상품의 가격은 0보다 크거나 같아야한다`() {
        assertThatThrownBy {
            Product(
                displayedName = "양념감자",
                price = ProductPrice(BigDecimal.valueOf(-1000))
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("상품의 가격은 0보다 작을 수 없습니다")
    }

    @Test
    fun `정상적인 상품 가격 변경`() {
        val product = product()

        product.changePrice(ProductPrice(BigDecimal.valueOf(5000)))

        val `5천원` = ProductPrice(BigDecimal.valueOf(5000))

        assertThat(product.price).isEqualTo(`5천원`)
    }
}
