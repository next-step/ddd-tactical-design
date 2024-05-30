package kitchenpos.eatinorders.tobe

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

class EatInOrderTest {
    @DisplayName("매장 주문은 주문 테이블을 점유한 상태여야 주문이 가능하다.")
    @Test
    fun test1() {
        shouldThrowExactly<IllegalStateException> {
            createEatInOrderFixture(orderTable = createOrderTableFixture(occupied = false))
        }
        shouldNotThrowAny {
            createEatInOrderFixture(orderTable = createOrderTableFixture(occupied = true))
        }
    }

    @DisplayName("주문 생성 시 초기 상태는 waiting 이어야 한다.")
    @Test
    fun test2() {
        val order = createEatInOrderFixture()
        order.status shouldBe EatInOrderStatus.WAITING
    }

    @DisplayName("waiting 상태에서만, accepted 상태가 될 수 있다.")
    @Test
    fun test3() {
        assertSoftly {
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.ACCEPTED).accept()
            }
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.SERVED).accept()
            }
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.COMPLETE).accept()
            }
            shouldNotThrowAny {
                createEatInOrderFixture(status = EatInOrderStatus.WAITING).accept()
            }
        }
    }

    @DisplayName("accepted 상태에서만, served 상태가 될 수 있다.")
    @Test
    fun test4() {
        assertSoftly {

            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.WAITING).served()
            }
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.SERVED).served()
            }
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.COMPLETE).served()
            }
            shouldNotThrowAny {
                createEatInOrderFixture(status = EatInOrderStatus.ACCEPTED).served()
            }
        }
    }

    @DisplayName("served 상태에서만, complete 상태가 될 수 있다.")
    @Test
    fun test5() {
        assertSoftly {
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.WAITING).complete()
            }
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.ACCEPTED).complete()
            }
            shouldThrowExactly<IllegalStateException> {
                createEatInOrderFixture(status = EatInOrderStatus.COMPLETE).complete()
            }
            shouldNotThrowAny {
                createEatInOrderFixture(status = EatInOrderStatus.SERVED).complete()
            }
        }
    }

    @DisplayName("매장 주문이 complete 상태가 되면, order table이 clear 된다.")
    @Test
    fun test6() {
        val order = createEatInOrderFixture(orderTable = createOrderTableFixture(occupied = true)).apply {
            this.status = EatInOrderStatus.SERVED
        }

        order.complete()

        order.orderTable.numberOfGuests shouldBe 0
        order.orderTable.occupied shouldBe false
    }

    @DisplayName("매장 주문의 주문 상품은 1개 이상 존재해야 한다.")
    @Test
    fun test7() {
        shouldThrowExactly<IllegalArgumentException> {
            createEatInOrderFixture(
                orderLineItems = OrderLineItems(emptyList()),
            )
        }
        shouldNotThrowAny {
            createEatInOrderFixture(
                orderLineItems = OrderLineItems(listOf(createOrderLineItem())),
            )
        }
    }

    private fun createEatInOrderFixture(
        orderLineItems: OrderLineItems = createOrderLineItemsFixture(),
        orderTable: OrderTable = createOrderTableFixture(),
        status: EatInOrderStatus = EatInOrderStatus.WAITING,
    ) = EatInOrder(
        orderLineItems = orderLineItems,
        orderTable = orderTable,
    ).apply {
        this.status = status
    }

    private fun createOrderLineItemsFixture(
        orderLineItems: List<OrderLineItem> = listOf(
            createOrderLineItem()
        ),
    ) = OrderLineItems(orderLineItems)

    private fun createOrderMenu(
        menuId: UUID = UUID.randomUUID(),
        price: Int = 10000,
    ) = OrderMenu(menuId = menuId, price = price)

    private fun createOrderLineItem(
        orderMenu: OrderMenu = createOrderMenu(),
        quantity: Int = 1,
    ) = OrderLineItem(
        orderMenu = orderMenu,
        quantity = quantity,
    )

    private fun createOrderTableFixture(
        occupied: Boolean = true,
    ) = OrderTable("1번 테이블").apply {
        this.changeNumberOfGuests(4)
        this.occupied = occupied
    }
}
