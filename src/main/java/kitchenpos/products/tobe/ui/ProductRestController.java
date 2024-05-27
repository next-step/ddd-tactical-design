package kitchenpos.products.tobe.ui;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.dto.request.ProductCreateRequest;
import kitchenpos.products.tobe.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    public ResponseEntity<ProductResponse> create(@RequestBody final ProductCreateRequest request) {
        ProductResponse response = productService.create(request);
        return ResponseEntity.created(pathAfterProductCreation(response.id())).body(response);
    }

    private URI pathAfterProductCreation(UUID id) {
        return URI.create("/api/products/" + id);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(
            @PathVariable final UUID productId,
            @RequestBody final BigDecimal price
    ) {
        return ResponseEntity.ok(productService.changePrice(productId, price));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
