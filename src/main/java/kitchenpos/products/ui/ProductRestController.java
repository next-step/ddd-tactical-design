package kitchenpos.products.ui;


import kitchenpos.products.application.ProductService;
import kitchenpos.products.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.ui.dto.ProductChangePriceResponse;
import kitchenpos.products.ui.dto.ProductCreateRequest;
import kitchenpos.products.ui.dto.ProductCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

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

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductChangePriceResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ProductChangePriceRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

//    @GetMapping
//    public ResponseEntity<List<Product>> findAll() {
//        return ResponseEntity.ok(productService.findAll());
//    }
}
