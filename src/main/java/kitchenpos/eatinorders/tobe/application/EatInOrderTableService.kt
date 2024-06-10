package kitchenpos.eatinorders.tobe.application

import kitchenpos.eatinorders.tobe.domain.*
import kitchenpos.eatinorders.tobe.dto.`in`.OrderTableCreateRequest
import kitchenpos.eatinorders.tobe.dto.out.OrderTableResponse
import kitchenpos.eatinorders.tobe.dto.out.fromOrderTable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EatInOrderTableService(
    private val orderTableRepository: OrderTableRepository,
    private val orderTableClearValidator: OrderTableClearValidator
) {
    @Transactional
    fun createOrderTable(orderTableCreateRequest: OrderTableCreateRequest): OrderTableResponse {
        val orderTable = OrderTable.of(OrderTableName(orderTableCreateRequest.name))

        return fromOrderTable(orderTableRepository.save(orderTable))
    }

    @Transactional
    fun sit(orderTableId: UUID): OrderTableResponse {
        val orderTable = orderTableRepository.findByIdOrNull(orderTableId)
            ?: throw NoSuchElementException("can not found order table: $orderTableId")

        orderTable.sit()

        return fromOrderTable(orderTable)
    }

    @Transactional
    fun clear(orderTableId: UUID): OrderTableResponse {
        val orderTable = orderTableRepository.findByIdOrNull(orderTableId)
            ?: throw NoSuchElementException("can not found order table: $orderTableId")

        orderTable.clear(orderTableClearValidator)

        return fromOrderTable(orderTable)
    }

    @Transactional
    fun changeNumberOfGuests(orderTableId: UUID, numberOfGuests: Int): OrderTableResponse {
        val orderTable = orderTableRepository.findByIdOrNull(orderTableId)
            ?: throw NoSuchElementException("can not found order table: $orderTableId")

        orderTable.changeNumberOfGuest(OrderTableGuestNumber(numberOfGuests))

        return fromOrderTable(orderTable)
    }
}
