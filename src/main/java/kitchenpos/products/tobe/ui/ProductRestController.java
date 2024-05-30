package kitchenpos.products.tobe.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import kitchenpos.products.tobe.application.ProductRequest;
import kitchenpos.products.tobe.application.ProductResponse;
import kitchenpos.products.tobe.application.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody final ProductRequest productRequest) {
        final ProductResponse response = productService.create(productRequest);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable final UUID productId,
                                                       @RequestBody final ProductRequest productRequest) {
        return ResponseEntity.ok(productService.changePrice(productId, productRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
