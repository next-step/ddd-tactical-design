package kitchenpos.product.tobe.domain

import java.math.BigDecimal
import kitchenpos.product.tobe.infra.DefaultProductNamePolicy
import kitchenpos.product.tobe.infra.FakeProfanities
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class ProductTest {
    private val productNamePolicy: ProductNamePolicy = DefaultProductNamePolicy(FakeProfanities())

    @Test
    @DisplayName("`Product`를 등록한다")
    fun createProduct() {
        val product = Product(
            productName = ProductName(productNamePolicy, "양념치킨"),
            productPrice = ProductPrice(BigDecimal.valueOf(16000))
        )

        assertThat(product.productName.name).isEqualTo("양념치킨")
        assertThat(product.productPrice.price).isEqualTo(BigDecimal.valueOf(16000))
    }

    @Test
    @DisplayName("`Product`를 등록할 때 `price`는 0원 미만일 수 없다")
    fun createProductPriceFail() {
        assertThatIllegalArgumentException().isThrownBy {
            Product(
                productName = ProductName(productNamePolicy, "양념치킨"),
                productPrice = ProductPrice(BigDecimal.valueOf(-1))
            )
        }
    }

    @Test
    @DisplayName("`Product`의 `price`를 변경한다")
    fun changePrice() {
        val product = Product(
            productName = ProductName(productNamePolicy, "양념치킨"),
            productPrice = ProductPrice(BigDecimal.valueOf(16000))
        )

        product.changePrice(ProductPrice(BigDecimal.valueOf(17000)))

        assertThat(product.productPrice.price).isEqualTo(BigDecimal.valueOf(17000))
    }

    @Test
    @DisplayName("`Product`의 `price`를 변경할 때 `price`는 0원 미만일 수 없다")
    fun changePriceFail() {
        val product = Product(
            productName = ProductName(productNamePolicy, "양념치킨"),
            productPrice = ProductPrice(BigDecimal.valueOf(16000))
        )

        assertThatIllegalArgumentException().isThrownBy {
            product.changePrice(ProductPrice(BigDecimal.valueOf(-1)))
        }
    }
}
