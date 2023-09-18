package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.application.request.ProductChangePriceRequest;
import kitchenpos.products.application.request.ProductCreateRequest;
import kitchenpos.products.application.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Validated @RequestBody final ProductCreateRequest request) {
        Product product = productService.create(request);
        final ProductResponse response = ProductResponse.of(product);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ProductChangePriceRequest request) {
        Product product = productService.changePrice(productId, request);
        return ResponseEntity.ok(ProductResponse.of(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> productResponses = productService.findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }
}
