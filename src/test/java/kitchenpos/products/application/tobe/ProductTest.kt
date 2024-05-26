package kitchenpos.products.application.tobe

import kitchenpos.common.price
import kitchenpos.products.application.FakeProductNameValidatorService
import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.productPrice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductTest {
    @Test
    fun `정상적인 상품 생성`() {
        val product = Product(
            displayedName = "양념감자",
            price = BigDecimal.valueOf(4000).price().productPrice(),
            FakeProductNameValidatorService()
        )

        val `4천원` = BigDecimal.valueOf(4000).price().productPrice()

        assertThat(product.displayedName).isEqualTo("양념감자")
        assertThat(product.price).isEqualTo(`4천원`)
    }
}
