package kitchenpos.products.tobe.ui;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.dto.ChangeProductPriceRequest;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import kitchenpos.products.tobe.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/tobe/products")
@RestController("TobeProductRestController")
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody final CreateProductRequest request) {
        final ProductResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ChangeProductPriceRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
