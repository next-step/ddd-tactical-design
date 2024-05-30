package kitchenpos.eatinorders.tobe

class OrderLineItems(
    val orderLineItemList: List<OrderLineItem>,
) {
    init {
        require(orderLineItemList.isNotEmpty())
    }
}
