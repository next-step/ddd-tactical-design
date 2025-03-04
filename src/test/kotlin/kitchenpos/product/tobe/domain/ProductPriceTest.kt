package kitchenpos.product.tobe.domain

import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ProductPriceTest {
    private lateinit var defaultProductPricePolicy: ProductPricePolicy

    @BeforeEach
    fun setUp() {
        defaultProductPricePolicy = ProductPricePolicy()
    }

    @Test
    @DisplayName("ProductPrice를 생성한다")
    fun createProductPrice() {
        val price = BigDecimal.valueOf(1000L)

        val productPrice = ProductPrice(defaultProductPricePolicy, price)

        assertThat(productPrice.price).isEqualTo(price)
    }

    @CsvSource(value = ["1000, 1001", "0, 2000"])
    @DisplayName("ProductPrice를 비교한다")
    @ParameterizedTest(name = "{0}원은 {1}원보다 작다")
    fun compareProductPrice(price: Long, otherPrice: Long) {
        val smallProductPrice = ProductPrice(defaultProductPricePolicy, BigDecimal.valueOf(price))
        val bigProductPrice = ProductPrice(defaultProductPricePolicy, BigDecimal.valueOf(otherPrice))

        assertThat(smallProductPrice).isLessThan(bigProductPrice)
    }

    @Test
    @DisplayName("ProductPrice의 1000원과 1000원은 같다")
    fun equalsProductPrice() {
        val price1 = ProductPrice(defaultProductPricePolicy, BigDecimal.valueOf(1000L))
        val price2 = ProductPrice(defaultProductPricePolicy, BigDecimal.valueOf(1000L))

        assertThat(price1).isEqualTo(price2)
    }

    @Test
    @DisplayName("ProductPrice는 0원 이상이어야 한다")
    fun createProductPriceFail() {
        assertThatIllegalArgumentException().isThrownBy {
            ProductPrice(defaultProductPricePolicy, BigDecimal.valueOf(-1))
        }
    }
}
