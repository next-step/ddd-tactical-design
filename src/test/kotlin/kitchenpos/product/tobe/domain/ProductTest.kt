package kitchenpos.product.tobe.domain

import java.math.BigDecimal
import kitchenpos.product.tobe.infra.FakeProfanities
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class ProductTest {
    @Test
    @DisplayName("`Product`를 등록한다")
    fun createProduct() {
        val product = Product(
            productName = ProductName(ProductNamePolicy(FakeProfanities()), "양념치킨"),
            price = BigDecimal.valueOf(16000)
        )

        assertThat(product.productName.name).isEqualTo("양념치킨")
        assertThat(product.price).isEqualTo(BigDecimal.valueOf(16000))
    }

    @Test
    @DisplayName("`Product`를 등록할 때 `price`는 0원 미만일 수 없다")
    fun createProductPriceFail() {
        assertThatIllegalArgumentException().isThrownBy {
            Product(
                productName = ProductName(ProductNamePolicy(FakeProfanities()), "양념치킨"),
                price = BigDecimal.valueOf(-1)
            )
        }
    }

    @Test
    @DisplayName("`Product`의 `price`를 변경한다")
    fun changePrice() {
        val product = Product(
            productName = ProductName(ProductNamePolicy(FakeProfanities()), "양념치킨"),
            price = BigDecimal.valueOf(16000)
        )

        product.changePrice(BigDecimal.valueOf(17000))

        assertThat(product.price).isEqualTo(BigDecimal.valueOf(17000))
    }

    @Test
    @DisplayName("`Product`의 `price`를 변경할 때 `price`는 0원 미만일 수 없다")
    fun changePriceFail() {
        val product = Product(
            productName = ProductName(ProductNamePolicy(FakeProfanities()), "양념치킨"),
            price = BigDecimal.valueOf(16000)
        )

        assertThatIllegalArgumentException().isThrownBy {
            product.changePrice(BigDecimal.valueOf(-1))
        }
    }
}
