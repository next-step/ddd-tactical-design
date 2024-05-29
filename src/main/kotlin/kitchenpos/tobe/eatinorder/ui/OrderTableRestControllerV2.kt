package kitchenpos.tobe.eatinorder.ui

import kitchenpos.tobe.eatinorder.application.OrderTableServiceV2
import kitchenpos.tobe.eatinorder.application.dto.request.CreateOrderTableRequest
import kitchenpos.tobe.eatinorder.application.dto.response.CreateOrderTableResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RequestMapping("/api/v2/order-tables")
@RestController
class OrderTableRestControllerV2(
    private val orderTableService: OrderTableServiceV2,
) {
    @PostMapping
    fun createOrderTable(request: CreateOrderTableRequest): ResponseEntity<CreateOrderTableResponse> {
        return ResponseEntity.created(URI.create("/api/order-tables")).body(
            orderTableService.createOrderTable(request),
        )
    }

    @PutMapping("/{orderTableId}/sit")
    fun sit(
        @PathVariable("orderTableId") orderTableId: UUID,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(orderTableService.sit(orderTableId))
    }

    @PutMapping("/{orderTableId}/clear")
    fun clear(
        @PathVariable("orderTableId") orderTableId: UUID,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(orderTableService.clear(orderTableId))
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    fun changeNumberOfGuests(
        @PathVariable orderTableId: UUID,
        numberOfGuests: Int,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(
            orderTableService.changeNumberOfGuests(orderTableId, numberOfGuests),
        )
    }
}
