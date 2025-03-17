package kitchenpos.order.tobe.eatinorder.domain

import org.assertj.core.api.Assertions.assertThatException
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OrderTableOccupancyTest {
    @Test
    @DisplayName("numberOfGuests는 0명 이상이어야 한다.")
    fun validateNumberOfGuests() {
        // when then
        assertAll(
            { assertThatException().isThrownBy { OrderTableOccupancy(-1, OrderTableStatus.EMPTY) } },
            { assertThatException().isThrownBy { OrderTableOccupancy(-1, OrderTableStatus.OCCUPIED) } },
        )
    }

    @Test
    @DisplayName("EmtpyTable의 numberOfGuests는 0명이다.")
    fun changeOccupiedToEmpty() {
        // given
        val orderTableOccupancy = OrderTableOccupancy.EMPTY

        // then
        assertAll(
            { assertEquals(orderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(orderTableOccupancy.status, OrderTableStatus.EMPTY) },
        )
    }

    @Test
    @DisplayName("EmtpyTable을 OccupiedTable로 변경한다")
    fun changeEmptyToOccupied() {
        // given
        val orderTableOccupancy = OrderTableOccupancy.EMPTY

        // when
        val changedOrderTableOccupancy = orderTableOccupancy.occupied()

        // then
        assertAll(
            { assertEquals(changedOrderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(changedOrderTableOccupancy.status, OrderTableStatus.OCCUPIED) },
        )
    }

    @Test
    @DisplayName("OccupiedTable의 numberOfGuest를 변경한다.")
    fun changeNumberOfGuest() {
        // given
        val orderTableOccupancy = OrderTableOccupancy(0, OrderTableStatus.OCCUPIED)

        // when
        val changedOrderTableOccupancy = orderTableOccupancy.changeNumberOfGuest(4)

        // then
        assertAll(
            { assertEquals(changedOrderTableOccupancy.numberOfGuests, 4) },
            { assertEquals(changedOrderTableOccupancy.status, OrderTableStatus.OCCUPIED) },
        )
    }

    @Test
    @DisplayName("EmptyTable의 numberOfGuest는 변경할 수 없다.")
    fun changeNumberOfGuestEmptyTableFail() {
        // given
        val orderTableOccupancy = OrderTableOccupancy(0, OrderTableStatus.EMPTY)

        // when then
        assertThatIllegalStateException()
            .isThrownBy { orderTableOccupancy.changeNumberOfGuest(4) }
    }
}
