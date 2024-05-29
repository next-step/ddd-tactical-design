package kitchenpos.tobe.eatinorder.infra

import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import kitchenpos.tobe.eatinorder.domain.repository.OrderTableRepositoryV2
import java.util.*

fun OrderTableRepositoryV2.getOrderTableById(orderTableId: UUID): OrderTableV2 {
    return this.findOrderTableById(orderTableId) ?: throw IllegalArgumentException("[$orderTableId] 주문 테이블을 찾을 수 없습니다.")
}
