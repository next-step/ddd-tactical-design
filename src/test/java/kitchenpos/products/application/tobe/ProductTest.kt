package kitchenpos.products.application.tobe

import kitchenpos.products.tobe.domain.Product
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductTest {

    @Test
    fun `정상적인 상품 생성`() {
        val product = Product(
            displayedName = "양념감자",
            price = BigDecimal.valueOf(4000)
        )

        assertThat(product.displayedName).isEqualTo("양념감자")
        assertThat(product.price).isEqualTo(BigDecimal.valueOf(4000))
    }

    @Test
    fun `상품의 가격은 0보다 크거나 같아야한다`() {
        assertThatThrownBy {
            Product(
                displayedName = "양념감자",
                price = BigDecimal.valueOf(-1000)
            )
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("상품의 가격은 0보다 작을 수 없습니다")
    }
}
