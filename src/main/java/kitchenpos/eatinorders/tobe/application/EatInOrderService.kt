package kitchenpos.eatinorders.tobe.application

import kitchenpos.eatinorders.tobe.domain.*
import kitchenpos.eatinorders.tobe.dto.`in`.EatInOrderCreateRequest
import kitchenpos.eatinorders.tobe.dto.out.EatInOrderResponse
import kitchenpos.eatinorders.tobe.dto.out.fromEatInOrder
import kitchenpos.eatinorders.tobe.event.EatInOrderCompleted
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EatInOrderService(
    private val eatInOrderRepository: EatInOrderRepository,
    private val eatInOrderCreateValidator: EatInOrderCreateValidator,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun createOrder(eatInOrderCreateRequest: EatInOrderCreateRequest): EatInOrderResponse {
        val eatInOrderLineItems = eatInOrderCreateRequest.eatInOrderLineItemCreateRequests
            .map { EatInOrderLineItem(it.menuId, EatInOrderLineItemQuantity(it.quantity), it.price) }
            .let { EatInOrderLineItems(it) }

        val eatInOrder = EatInOrder.of(
            eatInOrderCreateRequest.orderTableId,
            eatInOrderLineItems,
            eatInOrderCreateValidator
        )

        return fromEatInOrder(eatInOrderRepository.save(eatInOrder))
    }

    @Transactional
    fun accept(eatInOrderId: UUID) {
        val eatInOrder = eatInOrderRepository.findByIdOrNull(eatInOrderId)
            ?: throw NoSuchElementException("can not found eatInOrder: $eatInOrderId")

        eatInOrder.accept()
    }

    @Transactional
    fun serve(eatInOrderId: UUID) {
        val eatInOrder = eatInOrderRepository.findByIdOrNull(eatInOrderId)
            ?: throw NoSuchElementException("can not found eatInOrder: $eatInOrderId")

        eatInOrder.serve()
    }

    @Transactional
    fun complete(eatInOrderId: UUID) {
        val eatInOrder = eatInOrderRepository.findByIdOrNull(eatInOrderId)
            ?: throw NoSuchElementException("can not found eatInOrder: $eatInOrderId")

        eatInOrder.complete()

        applicationEventPublisher.publishEvent(EatInOrderCompleted(eatInOrder.id))
    }

}
