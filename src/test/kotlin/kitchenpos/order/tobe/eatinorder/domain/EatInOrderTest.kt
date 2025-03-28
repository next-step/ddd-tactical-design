package kitchenpos.order.tobe.eatinorder.domain

import java.util.*
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.order.tobe.common.OrderMenuInfo
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableEmptyService
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableOccupancy
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableStatus
import kitchenpos.utils.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.kotlin.or

class EatInOrderTest {
    private lateinit var menuInfo: OrderMenuInfo
    private lateinit var orderTable: OrderTable
    private lateinit var orderTableEmptyService: OrderTableEmptyService

    @BeforeEach
    fun setUp() {
        menuInfo = OrderMenuInfo(
            menuId = UUID.randomUUID(),
            menuDisplay = MenuDisplay.DISPLAYED,
        )
        orderTable = Fixtures.orderTable(
            orderTableOccupancy = OrderTableOccupancy(
                status = OrderTableStatus.OCCUPIED,
            ),
        )
        orderTableEmptyService = object : OrderTableEmptyService {
            override fun canEmpty(orderTable: OrderTable): Boolean {
                return true
            }
        }
    }


    @Test
    @DisplayName("EatInOrder를 생성한다")
    fun create() {
        val eatInOrder = EatInOrder.create(
            orderTable = orderTable,
            orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
        )

        assertAll(
            { assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.WAITING) },
            { assertThat(eatInOrder.orderTableId).isEqualTo(orderTable.id) },
        )
    }

    @Test
    @DisplayName("OrderTable이 EmptyTable인 경우 EatInOrder를 생성할 수 없다")
    fun createFailEmptyTable() {
        val orderTable = Fixtures.orderTable(
            orderTableOccupancy = OrderTableOccupancy(
                status = OrderTableStatus.EMPTY
            ),
        )

        assertThatIllegalStateException().isThrownBy {
            EatInOrder.create(
                orderTable = orderTable,
                orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
            )
        }
    }

    @Test
    @DisplayName("EatInOrder를 accept한다")
    fun accept() {
        val eatInOrder = EatInOrder(
            orderTableId = orderTable.id,
            orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
            status = EatInOrderStatus.WAITING
        )

        eatInOrder.accept()

        assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.ACCEPTED)
    }

    @ParameterizedTest
    @EnumSource(EatInOrderStatus::class, mode = EnumSource.Mode.EXCLUDE, names = ["WAITING"])
    @DisplayName("EatInOrder는 waiting 상태에서만 accept할 수 있다")
    fun acceptFail(status: EatInOrderStatus) {
        val eatInOrder = EatInOrder(
            orderTableId = orderTable.id,
            orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
            status = status
        )

        assertThatIllegalStateException().isThrownBy {
            eatInOrder.accept()
        }
    }

    @Test
    @DisplayName("EatInOrder를 serve한다")
    fun serve() {
        val eatInOrder = EatInOrder(
            orderTableId = orderTable.id,
            orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
            status = EatInOrderStatus.ACCEPTED
        )

        eatInOrder.serve()

        assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.SERVED)
    }

    @ParameterizedTest
    @EnumSource(EatInOrderStatus::class, mode = EnumSource.Mode.EXCLUDE, names = ["ACCEPTED"])
    @DisplayName("EatInOrder는 accepted 상태에서만 serve할 수 있다")
    fun serveFail(status: EatInOrderStatus) {
        val eatInOrder = EatInOrder(
            orderTableId = orderTable.id,
            orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
            status = status
        )

        assertThatIllegalStateException().isThrownBy {
            eatInOrder.serve()
        }
    }

    @Test
    @DisplayName("EatInOrder를 complete한다")
    fun complete() {
        val eatInOrder = EatInOrder(
            orderTableId = orderTable.id,
            orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
            status = EatInOrderStatus.SERVED
        )

        eatInOrder.complete(orderTable, orderTableEmptyService)

        assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.COMPLETED)
    }

    @ParameterizedTest
    @EnumSource(EatInOrderStatus::class, mode = EnumSource.Mode.EXCLUDE, names = ["SERVED"])
    @DisplayName("EatInOrder는 served 상태에서만 complete할 수 있다")
    fun completeFail(status: EatInOrderStatus) {
        val eatInOrder = EatInOrder(
            orderTableId = orderTable.id,
            orderLineItems = Fixtures.eatInOrderLineItems(menuId = menuInfo.menuId),
            status = status
        )

        assertThatIllegalStateException().isThrownBy {
            eatInOrder.complete(orderTable, orderTableEmptyService)
        }
    }
}
