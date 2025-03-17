package kitchenpos.order.tobe.eatinorder.domain

import java.util.*
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.order.tobe.common.OrderMenuInfo
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class EatInOrderTest {
    private lateinit var menuInfo: OrderMenuInfo
    private lateinit var orderTableInfo: EatInOrderOrderTableInfo

    @BeforeEach
    fun setUp() {
        menuInfo = OrderMenuInfo(
            menuId = UUID.randomUUID(),
            menuDisplay = MenuDisplay.DISPLAYED,
        )
        orderTableInfo = EatInOrderOrderTableInfo(
            orderTableId = UUID.randomUUID(),
            orderTableStatus = OrderTableStatus.OCCUPIED,
        )
    }


    @Test
    @DisplayName("EatInOrder를 생성한다")
    fun create() {
        val eatInOrder = EatInOrder.create(
            orderTableInfo = orderTableInfo,
            orderLineItems = EatInOrderLineItems(
                listOf(
                    EatInOrderLineItem(
                        seq = 1,
                        menuId = menuInfo.menuId,
                        quantity = 1,
                    )
                )
            ),
        )

        assertAll(
            { assertThat(eatInOrder.status).isEqualTo(EatInOrderStatus.WAITING) },
            { assertThat(eatInOrder.orderTableId).isEqualTo(orderTableInfo.orderTableId) },
        )
    }

    @Test
    @DisplayName("OrderTable이 EmptyTable인 경우 EatInOrder를 생성할 수 없다")
    fun createFailEmptyTable() {
        val orderTableInfo = EatInOrderOrderTableInfo(
            orderTableId = UUID.randomUUID(),
            orderTableStatus = OrderTableStatus.EMPTY,
        )

        assertThatIllegalStateException().isThrownBy {
            EatInOrder.create(
                orderTableInfo = orderTableInfo,
                orderLineItems = EatInOrderLineItems(
                    listOf(
                        EatInOrderLineItem(
                            seq = 1,
                            menuId = menuInfo.menuId,
                            quantity = 1,
                        )
                    )
                ),
            )
        }
    }
}
