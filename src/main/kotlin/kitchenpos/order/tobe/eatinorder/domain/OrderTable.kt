package kitchenpos.order.tobe.eatinorder.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "order_table")
@Entity(name = "TobeOrderTable")
class OrderTable(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(name = "name", nullable = false)
    var name: String,

    @Embedded
    var orderTableOccupancy: OrderTableOccupancy,
) {

    fun changeNumberOfGuest(numberOfGuests: Int) {
        orderTableOccupancy = orderTableOccupancy.changeNumberOfGuest(numberOfGuests)
    }

    fun occupied() {
        orderTableOccupancy = orderTableOccupancy.occupied()
    }

    fun empty() {
        this.orderTableOccupancy = OrderTableOccupancy.EMPTY
    }
}
