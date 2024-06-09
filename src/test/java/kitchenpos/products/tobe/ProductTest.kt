package kitchenpos.products.tobe

import kitchenpos.common.price
import kitchenpos.products.application.FakeProductNameValidator
import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductTest {
    @Test
    fun `정상적인 상품 생성`() {
        val product = Product(
            name = ProductName.of("양념감자", FakeProductNameValidator),
            price = BigDecimal.valueOf(4000).price()
        )

        val `4천원` = BigDecimal.valueOf(4000).price()

        assertThat(product.name.value).isEqualTo("양념감자")
        assertThat(product.price).isEqualTo(`4천원`)
    }
}
