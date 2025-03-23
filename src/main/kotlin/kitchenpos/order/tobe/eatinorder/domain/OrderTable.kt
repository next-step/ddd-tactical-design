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

    @Embedded
    val orderTableName: OrderTableName,

    @Embedded
    var orderTableOccupancy: OrderTableOccupancy,
) {

    fun changeNumberOfGuest(numberOfGuests: Int) {
        orderTableOccupancy = orderTableOccupancy.changeNumberOfGuest(numberOfGuests)
    }

    fun occupied() {
        orderTableOccupancy = orderTableOccupancy.occupied()
    }

    fun empty(orderTableEmptyService: OrderTableEmptyService) {
        if (!orderTableEmptyService.canEmpty(this)) {
            throw IllegalStateException("주문테이블에 완료되지않은 주문이 존재합니다.")
        }
        this.orderTableOccupancy = OrderTableOccupancy.EMPTY
    }

    fun emptyIfPossible(orderTableEmptyService: OrderTableEmptyService) {
        if (orderTableEmptyService.canEmpty(this)) {
            this.orderTableOccupancy = OrderTableOccupancy.EMPTY
        }
    }
}
