package kitchenpos.tobe.eatinorder.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kitchenpos.order.common.domain.OrderType
import kitchenpos.tobe.eatinorder.domain.EatInOrderStatus
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "orders")
class EatInOrder private constructor(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID,
    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    val type: OrderType = OrderType.EAT_IN,
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    var status: EatInOrderStatus,
    @Column(name = "order_date_time", nullable = false)
    val orderDateTime: LocalDateTime,
    @Embedded
    val orderLineItems: OrderLineItems,
    @ManyToOne
    @JoinColumn(
        name = "order_table_id",
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_orders_to_order_table"),
    )
    val orderTable: OrderTableV2,
) {
    companion object {
        fun from(
            orderDateTime: LocalDateTime,
            orderLineItems: List<OrderLineItemV2>,
            orderTable: OrderTableV2,
        ): EatInOrder {
            return EatInOrder(
                id = UUID.randomUUID(),
                status = EatInOrderStatus.WAITING,
                orderDateTime = orderDateTime,
                orderLineItems =
                    OrderLineItems.of(
                        orderLineItems,
                    ),
                orderTable = orderTable,
            )
        }
    }

    fun getOrderLineItems(): List<OrderLineItemV2> {
        return orderLineItems.orderLineItems
    }

    fun accept() {
        require(status == EatInOrderStatus.WAITING) { "[${this.id}] 대기 상태의 주문만 수락할 수 있습니다." }
        status = EatInOrderStatus.ACCEPTED
    }

    fun serve() {
        require(status == EatInOrderStatus.ACCEPTED) { "[${this.id}] 접수됨 상태의 주문만 제공할 수 있습니다." }
        status = EatInOrderStatus.SERVED
    }

    fun complete() {
        require(status == EatInOrderStatus.SERVED) { "[${this.id}] 제공됨 상태의 주문만 완료할 수 있습니다." }
        status = EatInOrderStatus.COMPLETED
    }
}
