package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Table(name = "eat_in_orders")
@Entity
class EatInOrder private constructor(orderTableId: UUID, eatInOrderLineItems: EatInOrderLineItems) {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    var status: EatInOrderStatus = EatInOrderStatus.WAITING
        private set

    @Column(name = "order_date_time", nullable = false)
    val orderDateTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "order_table_id")
    val orderTableId: UUID = orderTableId

    @Embedded
    val eatInOrderLineItems: EatInOrderLineItems = eatInOrderLineItems

    companion object {
        fun of(
            orderTableId: UUID,
            eatInOrderLineItems: EatInOrderLineItems,
            eatInOrderCreateValidator: EatInOrderCreateValidator
        ): EatInOrder {
            eatInOrderCreateValidator.validate(orderTableId, eatInOrderLineItems)

            return EatInOrder(orderTableId, eatInOrderLineItems)
        }
    }

    init {
        eatInOrderLineItems.apply(this)
    }

    fun accept() {
        if (this.status != EatInOrderStatus.WAITING) {
            throw IllegalStateException("접수대기 상태가 아닌 주문은 접수시킬수 없습니다")
        }

        this.status = EatInOrderStatus.ACCEPTED
    }

    fun serve() {
        if (this.status != EatInOrderStatus.ACCEPTED) {
            throw IllegalStateException("접수 상태가 아닌 주문은 전달시킬수 없습니다")
        }

        this.status = EatInOrderStatus.SERVED
    }

    fun complete() {
        if (this.status != EatInOrderStatus.SERVED) {
            throw IllegalStateException("전달 상태가 아닌 주문은 종료시킬수 없습니다")
        }

        this.status = EatInOrderStatus.COMPLETED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EatInOrder

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
