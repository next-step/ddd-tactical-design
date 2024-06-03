package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.JoinColumn

@Embeddable
class OrderLineItems(
    @ElementCollection
    @CollectionTable(
        name = "order_line_items", joinColumns = [
            JoinColumn(name = "order_id")
        ]
    )
    val orderLineItemList: List<OrderLineItem>,
) {
    init {
        require(orderLineItemList.isNotEmpty()) { "주문 상품은 1개 이상이어야 합니다." }
    }
}
