package kitchenpos.order.tobe.eatinorder.domain

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.util.*
import kitchenpos.order.tobe.common.Order
import kitchenpos.order.tobe.common.OrderType
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableEmptyService
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableStatus

@Entity(name = "TobeEatInOrder")
@DiscriminatorValue("EAT_IN")
class EatInOrder(
    @Embedded
    val orderLineItems: EatInOrderLineItems,

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(
        EnumType.STRING
    )
    var status: EatInOrderStatus,

    @Column(name = "order_table_id")
    val orderTableId: UUID

) : Order() {
    companion object {
        fun create(
            orderTable: OrderTable,
            orderLineItems: EatInOrderLineItems,
        ): EatInOrder {
            check(orderTable.orderTableOccupancy.status == OrderTableStatus.OCCUPIED) {
                "주문 테이블은 주문이 가능한 상태여야 합니다."
            }
            return EatInOrder(
                orderLineItems = orderLineItems,
                orderTableId = orderTable.id,
                status = EatInOrderStatus.WAITING
            )
        }
    }

    override val type: OrderType
        get() = OrderType.EAT_IN

    fun accept() {
        check(status == EatInOrderStatus.WAITING) {
            "접수할 수 있는 상태가 아닙니다."
        }
        status = EatInOrderStatus.ACCEPTED
    }

    fun serve() {
        check(status == EatInOrderStatus.ACCEPTED) {
            "서빙할 수 있는 상태가 아닙니다."
        }
        status = EatInOrderStatus.SERVED
    }

    fun complete(orderTable: OrderTable, orderTableEmptyService: OrderTableEmptyService) {
        check(status == EatInOrderStatus.SERVED) {
            "완료할 수 있는 상태가 아닙니다."
        }
        status = EatInOrderStatus.COMPLETED
        orderTable.emptyIfPossible(orderTableEmptyService)
    }
}
