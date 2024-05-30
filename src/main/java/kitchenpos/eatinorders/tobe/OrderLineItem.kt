package kitchenpos.eatinorders.tobe

class OrderLineItem(
    val orderMenu: OrderMenu,
    val quantity: Int,
) {
    val seq: Long? = null
}
