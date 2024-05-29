package kitchenpos.products.tobe.ui

import kitchenpos.products.tobe.application.ProductService
import kitchenpos.products.tobe.ui.dto.CreateProductRequest
import kitchenpos.products.tobe.ui.dto.ProductResponse
import kitchenpos.products.tobe.ui.dto.UpdateProductPriceRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RequestMapping("/api/products")
@RestController
class ProductRestController(
    private val productService: ProductService,
) {
    @PostMapping
    fun create(
        @RequestBody request: CreateProductRequest,
    ): ResponseEntity<ProductResponse> {
        val response = productService.create(request.name, request.price)
        return ResponseEntity.created(URI.create("/api/products/" + response.id))
            .body(response)
    }

    @PutMapping("/{productId}/price")
    fun changePrice(
        @PathVariable productId: UUID,
        @RequestBody request: UpdateProductPriceRequest,
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.changePrice(productId, request.price))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.findAll())
    }
}
