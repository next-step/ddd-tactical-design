package kitchenpos.products.application.tobe

import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductPrice
import kitchenpos.fixture.ProductFixtures.product
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
}
