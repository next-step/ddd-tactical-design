package kitchenpos.products.ui;

import java.net.URI;
import java.util.UUID;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.ui.request.ProductChangePriceRequest;
import kitchenpos.products.ui.request.ProductCreateRequest;
import kitchenpos.products.ui.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreateRequest request) {
        final ProductResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable UUID productId, @RequestBody final ProductChangePriceRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }
//
//    @GetMapping
//    public ResponseEntity<List<Product>> findAll() {
//        return ResponseEntity.ok(productService.findAll());
//    }
}
