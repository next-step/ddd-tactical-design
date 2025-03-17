package kitchenpos.order.tobe.eatinorder.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Embeddable
class EatInOrderLineItems(
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = ForeignKey(name = "fk_order_line_item_to_orders")
    )
    val orderLineItems: List<EatInOrderLineItem>,
) {
    init {
        require(orderLineItems.isNotEmpty()) { "주문 항목은 빈 목록이 될 수 없습니다." }
    }
}
