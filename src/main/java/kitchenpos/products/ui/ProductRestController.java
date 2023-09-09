package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody final ProductRequest request) {
        final Product response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
                .body(ProductResponse.fromEntity(response));
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable final UUID productId, @Valid @RequestBody final ProductChangePriceRequest request) {
        final Product response = productService.changePrice(productId, request.getPrice());
        return ResponseEntity.ok(ProductResponse.fromEntity(response));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        final List<ProductResponse> responses = productService.findAll()
                .stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(responses);
    }
}
