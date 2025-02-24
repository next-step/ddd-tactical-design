package kitchenpos.product.tobe.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal


class ProductTest {
    @Test
    @DisplayName("상품생성 / 성공")
    fun createProduct() {
        val product = Product(productName = ProductName(FakeProfanities(), "양념치킨"),price = BigDecimal.valueOf(16000))

        assertThat(product.productName.name).isEqualTo("양념치킨")
    }

    @Test
    @DisplayName("상품이름 미입력 / 상품생성 / 실패")
    fun createProductNameFail() {
        assertThatIllegalArgumentException().isThrownBy {
//            Product(name = "", price = BigDecimal.valueOf(16000))
        }
    }

    @Test
    @DisplayName("상품가격 0원 미만 / 상품생성 / 실패")
    fun createProductPriceFail() {
        assertThatIllegalArgumentException().isThrownBy {
//            Product(name = "양념치킨", price = BigDecimal.valueOf(-1))
        }
    }
}
