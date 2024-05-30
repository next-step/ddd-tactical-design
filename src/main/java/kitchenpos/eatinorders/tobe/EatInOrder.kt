package kitchenpos.eatinorders.tobe

import java.time.LocalDateTime
import java.util.*

class EatInOrder(
    val orderLineItems: OrderLineItems,
    val orderTable: OrderTable,
) {
    init {
        check(orderTable.canOrder())
    }

    val id = UUID.randomUUID()
    var status: EatInOrderStatus = EatInOrderStatus.WAITING
    val orderDateTime: LocalDateTime = LocalDateTime.now()

    fun accept() {
        checkStatusBeforeAccept()
        this.status = EatInOrderStatus.ACCEPTED
    }

    fun served() {
        checkStatusBeforeServed()
        this.status = EatInOrderStatus.SERVED
    }

    fun complete() {
        checkStatusBeforeComplete()
        this.status = EatInOrderStatus.COMPLETE
        this.orderTable.clear()
    }

    private fun checkStatusBeforeAccept() = check(status == EatInOrderStatus.WAITING)
    private fun checkStatusBeforeServed() = check(status == EatInOrderStatus.ACCEPTED)
    private fun checkStatusBeforeComplete() = check(status == EatInOrderStatus.SERVED)
}
