package kitchenpos.product.tobe.ui

import java.net.URI
import java.util.*
import kitchenpos.product.tobe.application.ProductService
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.application.dto.ProductResp
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/products")
@RestController
class ProductRestController(
    private val productService: ProductService
) {
    @PostMapping
    fun create(@RequestBody request: CreateProductReq): ResponseEntity<ProductResp> {
        val response = productService.create(request)
        return ResponseEntity.created(URI.create("/api/products/" + response.id))
            .body(response)
    }

    @PutMapping("/{productId}/price")
    fun changePrice(
        @PathVariable productId: UUID,
        @RequestBody request: ChangeProductPriceReq
    ): ResponseEntity<ProductResp> {
        return ResponseEntity.ok(productService.changePrice(productId, request))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<ProductResp>> {
        return ResponseEntity.ok(
            productService.findAll()
        )
    }
}
