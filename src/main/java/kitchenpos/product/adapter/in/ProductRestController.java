package kitchenpos.product.adapter.in;

import kitchenpos.product.application.ProductService;
import kitchenpos.product.application.port.in.ProductUseCase;
import kitchenpos.product.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

// TODO: Req, Res 클래스 사용
@RequestMapping("/api/products")
@RestController
class ProductRestController {
    private final ProductService productService;

    // TODO: ProductService 를 ProductUseCase 로 대체
    private final ProductUseCase productUseCase = null;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final Product request) {
        final Product response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<Product> changePrice(@PathVariable final UUID productId, @RequestBody final Product request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
