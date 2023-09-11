package kitchenpos.products.tobe.ui;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.ui.dto.ProductCreateRequest;
import kitchenpos.products.tobe.ui.dto.ProductCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponse> create(@RequestBody final ProductCreateRequest request) {
        final ProductCreateResponse response = productService.create(request);
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
