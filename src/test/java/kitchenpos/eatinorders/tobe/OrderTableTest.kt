package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.OrderTableGuestNumber
import kitchenpos.fixture.EatInOrderFixtures.orderTable
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class OrderTableTest {
    @Test
    fun `주문테이블 생성`() {
        val orderTable = orderTable()

        assertThat(orderTable.numberOfGuests).isEqualTo(OrderTableGuestNumber(0))
        assertThat(orderTable.occupied).isFalse()
    }

    @Test
    fun `주문 테이블의 좌석 수 변경`() {
        val orderTable = orderTable()

        orderTable.sit()
        orderTable.changeNumberOfGuest(OrderTableGuestNumber(10))

        assertThat(orderTable.numberOfGuests).isEqualTo(OrderTableGuestNumber(10))
    }

    @Test
    fun `미점유 중인 주문 테이블의 좌석 수 변경 불가능`() {
        val orderTable = orderTable()

        assertThatThrownBy {
            orderTable.changeNumberOfGuest(OrderTableGuestNumber(10))
        }.isInstanceOf(IllegalArgumentException::class.java).withFailMessage("미점유 중인 테이블의 고객 좌석 수를 변경할 수 없습니다")
    }
}
