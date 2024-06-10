package kitchenpos.eatinorders.tobe.domain

@JvmInline
value class OrderTableName(
    val value: String
) {
    init {
        if (value.isEmpty()){
            throw IllegalArgumentException("주문 테이블 이름은 공백일 수 없습니다")
        }
    }
}
