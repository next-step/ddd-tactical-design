package kitchenpos.product.tobe.domain

import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ProductPriceTest {
    @Test
    @DisplayName("ProductPrice를 생성한다")
    fun createProductPrice() {
        val price = BigDecimal.valueOf(1000L)

        val productPrice = ProductPrice(price)

        assertThat(productPrice.price).isEqualTo(price)
    }

    @Test
    @DisplayName("ProductPrice는 0원 이상이어야 한다")
    fun createProductPriceFail() {
        assertThatIllegalArgumentException().isThrownBy {
            ProductPrice(BigDecimal.valueOf(-1))
        }
    }
}
