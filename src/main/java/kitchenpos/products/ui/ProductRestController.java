package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.application.TobeProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.tobe.domain.TobeProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final TobeProductService productService;

    public ProductRestController(final TobeProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<TobeProduct> create(@RequestBody final ProductForm request) {
        final TobeProduct response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<TobeProduct> changePrice(@PathVariable final UUID productId, @RequestBody final ProductForm request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<TobeProduct>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
