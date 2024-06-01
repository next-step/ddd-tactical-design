package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "order_tables")
@Entity
class OrderTable(
    @Column(name = "name", nullable = false)
    val name: String,
) {
    init {
        require(name.isNotEmpty()) {
            "주문 테이블의 이름은 비어있으면 안됩니다."
        }
    }

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    val id = UUID.randomUUID()

    @Column(name = "occupied", nullable = false)
    var occupied: Boolean = false

    @Column(name = "number_of_guests", nullable = false)
    var numberOfGuests: Int = NUMBER_OF_GUEST_INITIAL_VALUE

    fun sit() {
        this.occupied = true
    }

    fun clear() {
        this.numberOfGuests = NUMBER_OF_GUEST_INITIAL_VALUE
        this.occupied = false
    }

    fun changeNumberOfGuests(numberOfGuests: Int) {
        check(numberOfGuests >= MINIMUM_NUMBER_OF_GUEST)
        this.numberOfGuests = numberOfGuests
    }

    fun canOrder() = occupied

    companion object {
        private const val MINIMUM_NUMBER_OF_GUEST = 0
        private const val NUMBER_OF_GUEST_INITIAL_VALUE = 0
    }
}