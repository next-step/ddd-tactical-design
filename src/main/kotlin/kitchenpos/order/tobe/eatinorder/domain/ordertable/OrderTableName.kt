package kitchenpos.order.tobe.eatinorder.domain.ordertable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class OrderTableName(
    @Column(name = "name", nullable = false)
    val name: String,
) {
    init {
        require(name.isNotEmpty()) { "주문 테이블 이름은 빈 문자열이 될 수 없습니다." }
    }

}
