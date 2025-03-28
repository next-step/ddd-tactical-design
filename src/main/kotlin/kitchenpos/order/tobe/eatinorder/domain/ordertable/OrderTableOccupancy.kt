package kitchenpos.order.tobe.eatinorder.domain.ordertable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class OrderTableOccupancy(
    @Column(name = "number_of_guests", nullable = false)
    val numberOfGuests: Int = 0,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "occupied", columnDefinition = "varchar(255)", nullable = false)
    val status: OrderTableStatus = OrderTableStatus.EMPTY,
) {
    init {
        validate(numberOfGuests, status)
    }

    companion object {
        val EMPTY = OrderTableOccupancy(0, OrderTableStatus.EMPTY)
    }

    fun changeNumberOfGuest(changeNumberOfGuests: Int): OrderTableOccupancy {
        if (status == OrderTableStatus.EMPTY) {
            throw IllegalStateException("빈 테이블은 손님수를 변경할 수 없습니다.")
        }
        return OrderTableOccupancy(changeNumberOfGuests, status)
    }

    fun occupied(): OrderTableOccupancy {
        return OrderTableOccupancy(numberOfGuests, OrderTableStatus.OCCUPIED)
    }

    private fun validate(numberOfGuest: Int, orderTableStatus: OrderTableStatus) {
        if (numberOfGuest < 0) {
            throw IllegalArgumentException("손님 수는 0명 이상이어야 합니다.")
        }
        if (orderTableStatus == OrderTableStatus.EMPTY && numberOfGuest > 0) {
            throw IllegalStateException("빈 테이블에는 손님을 할당할 수 없습니다.")
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderTableOccupancy

        if (numberOfGuests != other.numberOfGuests) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numberOfGuests
        result = 31 * result + status.hashCode()
        return result
    }
}
