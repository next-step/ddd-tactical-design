package kitchenpos.order.tobe.eatinorder.domain

import java.util.*
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableEmptyService
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableName
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableOccupancy
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableStatus
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThatIllegalStateException
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
            orderTableName = OrderTableName("테이블1"),
            orderTableOccupancy = OrderTableOccupancy(0, OrderTableStatus.EMPTY)
        )

        // then
        assertAll(
            { assertNotNull(orderTable.id) },
            { assertEquals(orderTable.orderTableName.name, "테이블1") },
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
        orderTable.empty(object : OrderTableEmptyService {
            override fun canEmpty(orderTable: OrderTable): Boolean {
                return true
            }
        })

        // then
        assertAll(
            { assertEquals(orderTable.orderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(orderTable.orderTableOccupancy.status, OrderTableStatus.EMPTY) }
        )
    }

    @Test
    @DisplayName("OccupiedTable을 EmptyTable로 변경할 수 없으면 에러가 발생한다")
    fun changeEmptyTableFail() {
        // given
        val orderTable = Fixtures.orderTable(orderTableOccupancy = OrderTableOccupancy(4, OrderTableStatus.OCCUPIED))

        // when
        assertThatIllegalStateException().isThrownBy {
            orderTable.empty(object : OrderTableEmptyService {
                override fun canEmpty(orderTable: OrderTable): Boolean {
                    return false
                }
            })
        }
    }

    @Test
    @DisplayName("OccupiedTable을 EmptyTable로 변경할 수 있으면 변경한다")
    fun changeEmptyTableIfPossible() {
        // given
        val orderTable = Fixtures.orderTable(orderTableOccupancy = OrderTableOccupancy(4, OrderTableStatus.OCCUPIED))
        val orderTable2 = Fixtures.orderTable(orderTableOccupancy = OrderTableOccupancy(4, OrderTableStatus.OCCUPIED))

        // when
        orderTable.emptyIfPossible(object : OrderTableEmptyService {
            override fun canEmpty(orderTable: OrderTable): Boolean {
                return true
            }
        })
        orderTable2.emptyIfPossible(object : OrderTableEmptyService {
            override fun canEmpty(orderTable: OrderTable): Boolean {
                return false
            }
        })

        // then
        assertAll(
            { assertEquals(orderTable.orderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(orderTable.orderTableOccupancy.status, OrderTableStatus.EMPTY) },
            { assertEquals(orderTable2.orderTableOccupancy.numberOfGuests, 4) },
            { assertEquals(orderTable2.orderTableOccupancy.status, OrderTableStatus.OCCUPIED) }
        )
    }
}
