package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.shared.dto.request.ProductChangePriceRequest;
import kitchenpos.products.shared.dto.request.ProductCreateRequest;
import kitchenpos.products.shared.dto.response.ProductDto;
import kitchenpos.products.tobe.domain.Product;
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
    public ResponseEntity<ProductDto> create(@RequestBody final ProductCreateRequest productCreateRequest) {
        final ProductDto response = productService.create(productCreateRequest);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductDto> changePrice(@PathVariable final UUID productId, @RequestBody final ProductChangePriceRequest productChangePriceRequest) {
        return ResponseEntity.ok(productService.changePrice(productId, productChangePriceRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
