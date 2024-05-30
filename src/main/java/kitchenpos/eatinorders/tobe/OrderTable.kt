package kitchenpos.eatinorders.tobe

import java.util.*

class OrderTable(
    val name: String,
) {
    init {
        require(name.isNotEmpty())
    }

    val id = UUID.randomUUID()
    var occupied: Boolean = false
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
