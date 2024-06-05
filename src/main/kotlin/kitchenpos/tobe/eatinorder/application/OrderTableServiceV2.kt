package kitchenpos.tobe.eatinorder.application

import kitchenpos.tobe.eatinorder.application.dto.request.CreateOrderTableRequest
import kitchenpos.tobe.eatinorder.application.dto.response.CreateOrderTableResponse
import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import kitchenpos.tobe.eatinorder.domain.repository.OrderTableRepositoryV2
import kitchenpos.tobe.eatinorder.infra.getOrderTableById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class OrderTableServiceV2(
    private val orderTableRepository: OrderTableRepositoryV2,
) {
    fun createOrderTable(request: CreateOrderTableRequest): CreateOrderTableResponse {
        val orderTable =
            OrderTableV2.of(
                id = request.id,
                name = request.name,
                numberOfGuests = request.numberOfGuests,
                occupied = request.occupied,
            )
        val saved = orderTableRepository.save(orderTable)
        return CreateOrderTableResponse.of(
            id = saved.id,
            name = saved.name,
            numberOfGuests = saved.numberOfGuests,
            occupied = saved.occupied,
        )
    }

    fun sit(orderTableId: UUID): UUID {
        val orderTable = orderTableRepository.getOrderTableById(orderTableId)
        orderTable.sit()
        return orderTable.id
    }

    fun clear(orderTableId: UUID): UUID {
        val orderTable = orderTableRepository.getOrderTableById(orderTableId)
        orderTable.clear()
        return orderTable.id
    }

    fun changeNumberOfGuests(
        orderTableId: UUID,
        numberOfGuests: Int,
    ): UUID {
        val orderTable = orderTableRepository.getOrderTableById(orderTableId)
        orderTable.changeNumberOfGuests(numberOfGuests)
        return orderTable.id
    }
}
