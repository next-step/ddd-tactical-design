package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.ui.dto.ProductRequest;
import kitchenpos.products.ui.dto.ProductResponse;
import kitchenpos.products.tobe.domain.ProductId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody final ProductRequest request) {
        final ProductResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable final ProductId productId, @Valid @RequestBody final ProductChangePriceRequest request) {
        final ProductResponse response = productService.changePrice(productId, request.getPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> responses = productService.findAll();
        return ResponseEntity.ok(responses);
    }
}
