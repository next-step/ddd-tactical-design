package kitchenpos.products.tobe.ui;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/tobe/products")
@RestController("tobeProductRestController")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final ProductCreateRequest request) {
        final Product product = productService.create(request);
        return ResponseEntity.created(URI.create("/api/tobe/products/" + product.getId()))
                .body(product);
    }

    @PostMapping("/{productId}/price")
    public ResponseEntity<Product> changePrice(@PathVariable final UUID productId, @RequestBody final ProductChangePriceRequest price) {
        return ResponseEntity.ok(productService.changePrice(productId, price));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("/ids")
    public ResponseEntity<List<ProductResponse>> listByIds(@RequestBody final List<UUID> productIds) {
        return ResponseEntity.ok(productService.listByIds(productIds));
    }
}
