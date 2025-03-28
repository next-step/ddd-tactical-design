package kitchenpos.order.tobe.eatinorder.domain

import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableName
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class OrderTableNameTest {
    @Test
    @DisplayName("OrderTableName을 생성한다.")
    fun create() {
        val orderTableName = OrderTableName("테이블1")
        assertEquals(orderTableName.name, "테이블1")
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("OrderTableName은 빈 문자열이 될 수 없다.")
    fun createWithEmptyName(name: String) {
        assertThatIllegalArgumentException().isThrownBy { OrderTableName(name) }
            .withMessage("주문 테이블 이름은 빈 문자열이 될 수 없습니다.")
    }

}

