package kitchenpos.product.adapter.in;

import kitchenpos.product.application.ProductService;
import kitchenpos.product.application.port.in.ProductUseCase;
import kitchenpos.product.adapter.out.persistence.ProductEntity;
import kitchenpos.product.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/products")
@RestController
class ProductRestController {
    private final ProductService productService;
    private final ProductUseCase productUseCase;

    public ProductRestController(ProductService productService, ProductUseCase productUseCase) {
        this.productService = productService;
        this.productUseCase = productUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody final ProductRequest request) {
        final Product product = productUseCase.create(ProductMapper.requestToDomain(request));
        final ProductResponse response = ProductMapper.domainToResponse(product);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(ProductMapper.domainToResponse(product));
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductEntity> changePrice(@PathVariable final UUID productId, @RequestBody final ProductEntity request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
