package kitchenpos.order.tobe.eatinorder.domain

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.util.*
import kitchenpos.order.tobe.common.Order
import kitchenpos.order.tobe.common.OrderType

@Entity(name = "TobeEatInOrder")
@DiscriminatorValue("EAT_IN")
class EatInOrder(
    @Embedded
    val orderLineItems: EatInOrderLineItems,

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(
        EnumType.STRING
    )
    var status: EatInOrderStatus,

    @Column(name = "order_table_id")
    val orderTableId: UUID,

    ) : Order() {
    override val type: OrderType
        get() = OrderType.EAT_IN
}
