package kitchenpos.product.adapter.in.rest;

import kitchenpos.product.application.port.in.ChangeProductPriceUseCase;
import kitchenpos.product.application.port.in.CreateProductUseCase;
import kitchenpos.product.application.port.in.LoadProductListUseCase;
import kitchenpos.product.application.service.model.ChangeProductPriceRequest;
import kitchenpos.product.application.service.model.CreateProductRequest;
import kitchenpos.product.domain.model.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final CreateProductUseCase createProductUseCase;
    private final ChangeProductPriceUseCase changeProductPriceUseCase;
    private final LoadProductListUseCase loadProductListUseCase;

    public ProductRestController(
            final CreateProductUseCase createProductUseCase,
            final ChangeProductPriceUseCase changeProductPriceUseCase, LoadProductListUseCase loadProductListUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.changeProductPriceUseCase = changeProductPriceUseCase;
        this.loadProductListUseCase = loadProductListUseCase;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final CreateProductRequest request) {
        final Product response = createProductUseCase.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<Product> changePrice(@PathVariable("productId") final UUID productId, @RequestBody final ChangeProductPriceRequest request) {
        return ResponseEntity.ok(changeProductPriceUseCase.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(@RequestParam(value = "ids", required = false) List<UUID> ids) {
        return ResponseEntity.ok(loadProductListUseCase.findAll(ids));
    }
}
