package kitchenpos.common

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal

class PriceTest {
    @Test
    fun `가격은 0보다 크거나 같아야한다`() {
        Assertions.assertThatThrownBy {
            BigDecimal.valueOf(-1000L).price()
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("상품의 가격은 0보다 작을 수 없습니다")
    }

    @ValueSource(longs = [0L, 1L, 1000L])
    @ParameterizedTest
    fun `정상적인 가격 생성`(price: Long) {
        assertDoesNotThrow { BigDecimal.valueOf(price).price() }
    }
}
