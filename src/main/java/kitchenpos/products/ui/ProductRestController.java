package kitchenpos.products.ui;

import java.net.URI;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.ui.request.ProductRequest;
import kitchenpos.products.ui.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        final ProductResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

//    @PutMapping("/{productId}/price")
//    public ResponseEntity<Product> changePrice(@PathVariable final UUID productId, @RequestBody final Product request) {
//        return ResponseEntity.ok(productService.changePrice(productId, request));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Product>> findAll() {
//        return ResponseEntity.ok(productService.findAll());
//    }
}
