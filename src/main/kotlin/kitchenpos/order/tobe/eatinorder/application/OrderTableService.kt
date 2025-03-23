package kitchenpos.order.tobe.eatinorder.application

import java.util.*
import kitchenpos.order.tobe.eatinorder.application.dto.CreateOrderTableReq
import kitchenpos.order.tobe.eatinorder.domain.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.OrderTableEmptyService
import kitchenpos.order.tobe.eatinorder.domain.OrderTableName
import kitchenpos.order.tobe.eatinorder.domain.OrderTableOccupancy
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("tobeOrderTableService")
class OrderTableService(
    private val orderTableRepository: OrderTableRepository,
    private val orderTableEmptyService: OrderTableEmptyService
) {

    @Transactional
    fun create(req: CreateOrderTableReq): UUID {
        return orderTableRepository.save(
            OrderTable(
                orderTableName = OrderTableName(req.name),
                orderTableOccupancy = OrderTableOccupancy.EMPTY
            )
        ).id
    }

    @Transactional
    fun occupied(orderTableId: UUID) {
        val orderTable =
            orderTableRepository.findById(orderTableId).orElseThrow { NoSuchElementException("주문 테이블을 찾을 수 없습니다.") }
        orderTable.occupied()
    }

    @Transactional
    fun empty(orderTableId: UUID) {
        val orderTable =
            orderTableRepository.findById(orderTableId).orElseThrow { NoSuchElementException("주문 테이블을 찾을 수 없습니다.") }
        orderTable.empty(orderTableEmptyService)
    }

    @Transactional
    fun changeNumberOfGuest(orderTableId: UUID, numberOfGuest: Int) {
        val orderTable =
            orderTableRepository.findById(orderTableId).orElseThrow { NoSuchElementException("주문 테이블을 찾을 수 없습니다.") }
        orderTable.changeNumberOfGuest(numberOfGuest)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<OrderTable> {
        return orderTableRepository.findAll()
    }
}
