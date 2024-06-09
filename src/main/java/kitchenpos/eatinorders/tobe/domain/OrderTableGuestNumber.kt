package kitchenpos.eatinorders.tobe.domain

@JvmInline
value class OrderTableGuestNumber(
    val value: Int
) {
    init {
        if (value < 0) {
            throw IllegalArgumentException("주문 테이블의 방문 고객 수는 0보다 크거나 같아야합니다")
        }
    }

    companion object {
        val ZERO = OrderTableGuestNumber(0)
    }
}
