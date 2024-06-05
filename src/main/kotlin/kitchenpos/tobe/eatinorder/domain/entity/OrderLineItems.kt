package kitchenpos.tobe.eatinorder.domain.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Embeddable
class OrderLineItems private constructor(
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_order_line_item_to_orders"),
    )
    val orderLineItems: List<OrderLineItemV2>,
) {
    companion object {
        fun from(orderLineItems: List<OrderLineItemV2>): OrderLineItems {
            return OrderLineItems(orderLineItems = orderLineItems)
        }
    }
}
