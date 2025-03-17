package kitchenpos.order.tobe.eatinorder.application

import java.util.*
import kitchenpos.order.tobe.eatinorder.application.dto.CreateOrderTableReq
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.order.tobe.eatinorder.domain.OrderTableOccupancy
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository
import kitchenpos.order.tobe.eatinorder.domain.OrderTableStatus
import kitchenpos.order.tobe.eatinorder.infra.FakeEatInOrderRepository
import kitchenpos.order.tobe.eatinorder.infra.FakeOrderTableRepository
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OrderTableServiceTest {
    private lateinit var orderTableService: OrderTableService
    private lateinit var orderTableRepository: OrderTableRepository
    private lateinit var eatInOrderRepository: EatInOrderRepository

    @BeforeEach
    fun setUp() {
        orderTableRepository = FakeOrderTableRepository()
        eatInOrderRepository = FakeEatInOrderRepository()
        orderTableService = OrderTableService(orderTableRepository, eatInOrderRepository)
    }

    @Test
    @DisplayName("OrderTable을 생성한다.")
    fun create() {
        // given
        val req = CreateOrderTableReq("테이블1")

        // when
        val id = orderTableService.create(req)

        // then
        val createdOrderTable = orderTableRepository.findById(id).orElseThrow()
        assertAll(
            { assertNotNull(createdOrderTable.id) },
            { assertEquals(createdOrderTable.orderTableName.name, "테이블1") },
            { assertEquals(createdOrderTable.orderTableOccupancy.numberOfGuests, 0) },
            { assertEquals(createdOrderTable.orderTableOccupancy.status, OrderTableStatus.EMPTY) }
        )
    }

    @Test
    @DisplayName("OrderTable을 OccupiedTable로 변경한다")
    fun occupied() {
        // given
        val orderTable = orderTableRepository.save(Fixtures.orderTable())

        // when
        orderTableService.occupied(orderTable.id)

        // then
        val occupiedOrderTable = orderTableRepository.findById(orderTable.id).orElseThrow()
        assertEquals(occupiedOrderTable.orderTableOccupancy.status, OrderTableStatus.OCCUPIED)
    }

    @Test
    @DisplayName("존재하지않는 OrderTable은 Occupied 할 수 없다")
    fun occupiedInvalidOrderTable() {
        // when then
        assertThatThrownBy { orderTableService.occupied(Fixtures.INVALID_UUID) }
            .isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("주문 테이블을 찾을 수 없습니다.")
    }

    @Test
    @DisplayName("OrderTable을 EmptyTable로 변경한다")
    fun empty() {
        // given
        val orderTable = orderTableRepository.save(
            Fixtures.orderTable(
                orderTableOccupancy = OrderTableOccupancy(
                    4,
                    OrderTableStatus.OCCUPIED
                )
            )
        )


        // when
        orderTableService.empty(orderTable.id)

        // then
        val emptyOrderTable = orderTableRepository.findById(orderTable.id).orElseThrow()
        assertEquals(emptyOrderTable.orderTableOccupancy.status, OrderTableStatus.EMPTY)
        assertEquals(emptyOrderTable.orderTableOccupancy.numberOfGuests, 0)
    }

    @Test
    @DisplayName("존재하지않는 OrderTable은 Empty 할 수 없다")
    fun emptyInvalidOrderTable() {
        // when then
        assertThatThrownBy { orderTableService.empty(Fixtures.INVALID_UUID) }
            .isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("주문 테이블을 찾을 수 없습니다.")
    }

    @Test
    @DisplayName("Complete되지 않은 EatInOrder가 있는 OrderTable은 EmptyTable로 변경할 수 없다")
    fun changeEmptyTableFailOrderNotComplete() {
        // given
        val orderTable = orderTableRepository.save(
            Fixtures.orderTable(
                orderTableOccupancy = OrderTableOccupancy(
                    4,
                    OrderTableStatus.OCCUPIED
                )
            )
        )
        eatInOrderRepository.save(
            Fixtures.eatInOrder(
                menuId = UUID.randomUUID(),
                orderTableId = orderTable.id,
                status = EatInOrderStatus.WAITING
            )
        )

        // when then
        assertThatThrownBy { orderTableService.empty(orderTable.id) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("주문테이블에 완료되지않은 주문이 존재합니다.")
    }

    @Test
    @DisplayName("OccupiedTable의 numberOfGuest를 변경한다")
    fun changeNumberOfGuestOccupiedTable() {
        // given
        val orderTable = orderTableRepository.save(
            Fixtures.orderTable(
                orderTableOccupancy = OrderTableOccupancy(
                    0,
                    OrderTableStatus.OCCUPIED
                )
            )
        )

        // when
        orderTableService.changeNumberOfGuest(orderTable.id, 4)

        // then
        val changedOrderTable = orderTableRepository.findById(orderTable.id).orElseThrow()
        assertEquals(changedOrderTable.orderTableOccupancy.numberOfGuests, 4)
    }

    @Test
    @DisplayName("EmptyTable은 numberOfGuests를 변경할 수 없다")
    fun changeNumberOfGuestEmptyTableFail() {
        // given
        val orderTable = orderTableRepository.save(
            Fixtures.orderTable(
                orderTableOccupancy = OrderTableOccupancy(
                    0,
                    OrderTableStatus.EMPTY
                )
            )
        )

        // when then
        assertThatThrownBy { orderTableService.changeNumberOfGuest(orderTable.id, 4) }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("빈 테이블은 손님수를 변경할 수 없습니다.")
    }


    @Test
    @DisplayName("존재하지않는 OrderTable은 numberOfGuests를 변경할 수 없다")
    fun changeNumberOfGuestInvalidOrderTable() {
        // when then
        assertThatThrownBy { orderTableService.changeNumberOfGuest(Fixtures.INVALID_UUID, 4) }
            .isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("주문 테이블을 찾을 수 없습니다.")
    }

    @Test
    @DisplayName("OrderTable 목록을 조회한다")
    fun findAll() {
        // given
        orderTableRepository.save(Fixtures.orderTable())
        orderTableRepository.save(Fixtures.orderTable())

        // when
        val orderTables = orderTableService.findAll()

        // then
        assertThat(orderTables).hasSize(2)
    }
}
