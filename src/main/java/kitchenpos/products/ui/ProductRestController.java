package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.dto.ChangePriceRequest;
import kitchenpos.products.dto.CreateRequest;
import kitchenpos.products.dto.ProductDto;
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
    public ResponseEntity<ProductDto> create(@RequestBody final CreateRequest request) {
        final ProductDto response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductDto> changePrice(@PathVariable final UUID productId, @RequestBody final ChangePriceRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
