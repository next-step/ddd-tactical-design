package kitchenpos.order.tobe.eatinorder.domain

import java.util.*
import kitchenpos.utils.Fixtures
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OrderTableTest {
    @Test
    @DisplayName("OrderTable을 생성한다.")
    fun create() {
        // when
        val orderTable = OrderTable(
            id = UUID.randomUUID(),
            name = "테이블1",
            orderTableOccupancy = OrderTableOccupancy(0, OrderTableStatus.EMPTY)
        )

        // then
        assertAll(
            { assertNotNull(orderTable.id) },
            { assertEquals(orderTable.name, "테이블1") },
            { assertEquals(orderTable.orderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(orderTable.orderTableOccupancy.status, OrderTableStatus.EMPTY) }
        )
    }

    @Test
    @DisplayName("OccupiedTable의 numberOfGuest를 변경한다.")
    fun changeNumberOfGuestOccupiedTable() {
        // given
        val orderTable = Fixtures.orderTable(orderTableOccupancy = OrderTableOccupancy(0, OrderTableStatus.OCCUPIED))

        // when
        orderTable.changeNumberOfGuest(4)

        // then
        assertEquals(orderTable.orderTableOccupancy.numberOfGuests, 4)
    }


    @Test
    @DisplayName("EmptyTable을 OccupiedTable로 변경한다")
    fun changeOccupiedTable() {
        // given
        val orderTable = Fixtures.orderTable(orderTableOccupancy = OrderTableOccupancy(0, OrderTableStatus.EMPTY))

        // when
        orderTable.occupied()

        // then
        assertAll(
            { assertEquals(orderTable.orderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(orderTable.orderTableOccupancy.status, OrderTableStatus.OCCUPIED) }
        )
    }

    @Test
    @DisplayName("OccupiedTable을 EmptyTable로 변경한다")
    fun changeEmptyTable() {
        // given
        val orderTable = Fixtures.orderTable(orderTableOccupancy = OrderTableOccupancy(4, OrderTableStatus.OCCUPIED))

        // when
        orderTable.empty()

        // then
        assertAll(
            { assertEquals(orderTable.orderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(orderTable.orderTableOccupancy.status, OrderTableStatus.EMPTY) }
        )
    }
}
