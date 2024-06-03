package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Table(name = "eat_in_orders")
@Entity
class EatInOrder(
    @Embedded
    val orderLineItems: OrderLineItems,
    @ManyToOne
    @JoinColumn(name = "order_table_id")
    val orderTable: OrderTable,
) {
    init {
        check(orderTable.canOrder()) { "매장 식사는 테이블을 점유한 상태에서 주문이 가능합니다." }
    }

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    val id: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    var status: EatInOrderStatus = EatInOrderStatus.WAITING

    @Column(name = "order_date_time", nullable = false)
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

    private fun checkStatusBeforeAccept() = check(status == EatInOrderStatus.WAITING) {
        "현재 주문 상태가 waiting 일 때만, accept 상태로 변경 가능합니다."
    }

    private fun checkStatusBeforeServed() = check(status == EatInOrderStatus.ACCEPTED) {
        "현재 주문 상태가 accept 일 때만, served 상태로 변경 가능합니다."
    }

    private fun checkStatusBeforeComplete() = check(status == EatInOrderStatus.SERVED) {
        "현재 주문 상태가 served 일 때만, complete 상태로 변경 가능합니다."
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EatInOrder) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
