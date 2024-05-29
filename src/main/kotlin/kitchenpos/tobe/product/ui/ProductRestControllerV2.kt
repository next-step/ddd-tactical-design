package kitchenpos.tobe.product.ui

import kitchenpos.tobe.product.application.ProductServiceV2
import kitchenpos.tobe.product.application.dto.request.ChangeProductPriceRequest
import kitchenpos.tobe.product.application.dto.request.CreateProductRequest
import kitchenpos.tobe.product.application.dto.response.ChangeProductPriceResponse
import kitchenpos.tobe.product.application.dto.response.CreateProductResponse
import kitchenpos.tobe.product.application.dto.response.GetProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RequestMapping("/api/v2/products")
@RestController
class ProductRestControllerV2(
    private val productServiceV2: ProductServiceV2,
) {
    @PostMapping
    fun create(
        @RequestBody request: CreateProductRequest,
    ): ResponseEntity<CreateProductResponse> {
        val response = productServiceV2.create(request)
        return ResponseEntity.created(URI.create("/api/products/" + response.id))
            .body(response)
    }

    @PutMapping("/{productId}/price")
    fun changePrice(
        @PathVariable productId: UUID,
        @RequestBody request: ChangeProductPriceRequest,
    ): ResponseEntity<ChangeProductPriceResponse> {
        return ResponseEntity.ok(productServiceV2.changePrice(productId, request))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<GetProductResponse>> {
        return ResponseEntity.ok(productServiceV2.findAll())
    }
}
