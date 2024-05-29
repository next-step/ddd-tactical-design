package kitchenpos.tobe.eatinorder.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "order_table")
@Entity
class OrderTableV2(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "number_of_guests", nullable = false)
    var numberOfGuests: Int = 0,
    @Column(name = "occupied", nullable = false)
    var occupied: Boolean = false,
) {
    companion object {
        fun from(
            id: UUID,
            name: String,
            numberOfGuests: Int,
            occupied: Boolean,
        ): OrderTableV2 {
            return OrderTableV2(
                id = id,
                name = name,
                numberOfGuests = numberOfGuests,
                occupied = occupied,
            )
        }
    }

    fun clear() {
        numberOfGuests = 0
        occupied = false
    }

    fun sit() {
        occupied = true
    }

    fun changeNumberOfGuests(numberOfGuests: Int) {
        require(numberOfGuests >= 0) { "고객 수는 0명 이상이어야 합니다." }
        require(occupied) { "점유되지않은 주문 테이블의 고객 수는 변경할 수 없습니다." }
        this.numberOfGuests = numberOfGuests
    }
}
