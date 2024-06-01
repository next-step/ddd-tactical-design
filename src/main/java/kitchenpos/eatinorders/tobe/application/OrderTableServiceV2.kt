package kitchenpos.eatinorders.tobe.application

import kitchenpos.eatinorders.tobe.domain.OrderTable
import kitchenpos.eatinorders.tobe.domain.OrderTableRepositoryV2
import kitchenpos.eatinorders.tobe.dto.ChangeNumberOfGuestsRequest
import kitchenpos.eatinorders.tobe.dto.CreateOrderTableRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service
class OrderTableServiceV2(
    private val orderTableRepository: OrderTableRepositoryV2,
) {
    fun create(
        request: CreateOrderTableRequest,
    ) {
        val orderTable = OrderTable(name = request.name)
        orderTableRepository.save(orderTable)
    }

    fun sit(
        orderTableId: UUID,
    ): OrderTable {
        val orderTable = getOrderTable(orderTableId)
        orderTable.sit()
        return orderTable
    }

    fun changeNumberOfGuests(
        orderTableId: UUID,
        request: ChangeNumberOfGuestsRequest,
    ): OrderTable {
        val orderTable = getOrderTable(orderTableId)
        orderTable.changeNumberOfGuests(request.numberOfGuests)
        return orderTable
    }

    @Transactional(readOnly = true)
    fun findAll() = orderTableRepository.findAll()

    private fun getOrderTable(
        orderTableId: UUID,
    ) = orderTableRepository.findByIdOrNull(orderTableId) ?: throw NoSuchElementException()
}
