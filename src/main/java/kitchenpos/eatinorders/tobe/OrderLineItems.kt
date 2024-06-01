package kitchenpos.eatinorders.tobe

class OrderLineItems(
    val orderLineItemList: List<OrderLineItem>,
) {
    init {
        require(orderLineItemList.isNotEmpty()) { "주문 상품은 1개 이상이어야 합니다." }
    }
}
