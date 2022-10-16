package kitchenpos.products.ui;

import kitchenpos.products.application.ChangeProductPriceService;
import kitchenpos.products.application.CreateProductService;
import kitchenpos.products.application.FindAllProductService;
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
    private final FindAllProductService findAllProductService;

    private final CreateProductService createProductService;

    private final ChangeProductPriceService changeProductPriceService;

    public ProductRestController(
        final FindAllProductService findAllProductService,
        final CreateProductService createProductService,
        final ChangeProductPriceService changeProductPriceService
    ) {
        this.findAllProductService = findAllProductService;
        this.createProductService = createProductService;
        this.changeProductPriceService = changeProductPriceService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final CreateProductRequest request) {
        final Product response = createProductService.create(request.price, request.name);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<Product> changePrice(@PathVariable final UUID productId, @RequestBody final ChangePriceRequest request) {
        return ResponseEntity.ok(changeProductPriceService.changePrice(productId, request.price));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(findAllProductService.findAll());
    }
}
