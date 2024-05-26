package kitchenpos.products.application.tobe

import kitchenpos.common.price
import kitchenpos.fixture.ProductFixtures
import kitchenpos.products.tobe.domain.productPrice
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class ProductPriceTest {
    @Test
    fun `상품의 가격은 0보다 크거나 같아야한다`() {
        Assertions.assertThatThrownBy {
            BigDecimal.valueOf(-1000L).price().productPrice()
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("상품의 가격은 0보다 작을 수 없습니다")
    }

    @ValueSource(longs = [0L, 1L, 1000L])
    @ParameterizedTest
    fun `정상적인 상품 가격 변경`(price: Long) {
        val product = ProductFixtures.product()

        product.changePrice(BigDecimal.valueOf(price).price().productPrice())

        val `상품가격` = BigDecimal.valueOf(price).price().productPrice()

        Assertions.assertThat(product.price).isEqualTo(`상품가격`)
    }
}
