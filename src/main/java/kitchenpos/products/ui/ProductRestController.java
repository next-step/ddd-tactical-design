package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.ui.dto.ChangePriceRequest;
import kitchenpos.products.ui.dto.CreateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final CreateProductRequest request) {
        final Product response = productService.create(request.price, request.name);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<Product> changePrice(@PathVariable final UUID productId, @RequestBody final ChangePriceRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request.price));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
