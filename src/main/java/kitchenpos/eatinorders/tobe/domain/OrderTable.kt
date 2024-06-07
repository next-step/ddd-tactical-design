package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kitchenpos.eatinorders.tobe.domain.OrderTableGuestNumber.Companion.ZERO
import java.util.*

@Table(name = "order_table")
@Entity
class OrderTable private constructor(name: OrderTableName) {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID = UUID.randomUUID()

    @Column(name = "name", nullable = false)
    val name: OrderTableName = name

    @Column(name = "number_of_guests", nullable = false)
    var numberOfGuests: OrderTableGuestNumber = ZERO
        private set

    @Column(name = "occupied", nullable = false)
    var occupied: Boolean = false
        private set

    companion object {
        fun of(name: OrderTableName): OrderTable {
            return OrderTable(name)
        }
    }

    fun sit() {
        occupied = true
    }

    fun clear(orderTableClearValidator: OrderTableClearValidator) {
        orderTableClearValidator.validate(this)

        numberOfGuests = ZERO
        occupied = false
    }

    fun changeNumberOfGuest(numberOfGuests: OrderTableGuestNumber) {
        if (!occupied) {
            throw IllegalArgumentException("미점유 중인 테이블의 고객 좌석 수를 변경할 수 없습니다")
        }

        this.numberOfGuests = numberOfGuests
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderTable

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
