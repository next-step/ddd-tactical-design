package kitchenpos.tobe.eatinorder.ui

import kitchenpos.tobe.eatinorder.application.dto.request.CreateOrderRequest
import kitchenpos.tobe.eatinorder.application.dto.response.CreateEatInOrderResponse
import kitchenpos.tobe.eatinorder.application.dto.response.GetOrderResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URI
import java.util.*

@RequestMapping("/api/v2/orders")
class EatInOrderRestController(
    private val eatInOrderService: kitchenpos.tobe.eatinorder.application.EatInOrderService,
) {
    @PostMapping
    fun createOrder(request: CreateOrderRequest): ResponseEntity<CreateEatInOrderResponse> {
        return ResponseEntity.created(URI.create("/api/v2/orders")).body(
            eatInOrderService.createOrder(request),
        )
    }

    @PutMapping("/{orderId}/accept")
    fun accept(
        @PathVariable("orderId") orderId: UUID,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(eatInOrderService.accept(orderId))
    }

    @PutMapping("/{orderId}/serve")
    fun serve(
        @PathVariable("orderId") orderId: UUID,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(eatInOrderService.serve(orderId))
    }

    @PutMapping("/{orderId}/complete")
    fun complete(
        @PathVariable("orderId") orderId: UUID,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(eatInOrderService.complete(orderId))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<GetOrderResponse>> {
        return ResponseEntity.ok(eatInOrderService.findAll())
    }
}
