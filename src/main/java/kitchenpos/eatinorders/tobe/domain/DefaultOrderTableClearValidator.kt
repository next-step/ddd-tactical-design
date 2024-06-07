package kitchenpos.eatinorders.tobe.domain

import org.springframework.stereotype.Service

@Service
class DefaultOrderTableClearValidator(
    private val eatInOrderRepository: EatInOrderRepository
) : OrderTableClearValidator {
    override fun validate(orderTable: OrderTable) {
        if (eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTable.id, EatInOrderStatus.COMPLETED)) {
            throw IllegalStateException("완료되지 않은 주문이 있는 경우 주문 테이블이 비어있을 수 없습니다")
        }
    }

    override fun isValid(orderTable: OrderTable): Boolean {
        return eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTable.id, EatInOrderStatus.COMPLETED)
    }
}
