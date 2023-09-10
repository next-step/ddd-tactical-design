package kitchenpos.product.adapter.in;

import kitchenpos.product.application.port.in.ProductUseCase;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/products")
@RestController
class ProductRestController {
    private final ProductUseCase productUseCase;

    ProductRestController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody final CreateProductRequest request) {
        final Product product = productUseCase.create(ProductMapper.requestToDomain(request));
        final ProductResponse response = ProductMapper.domainToResponse(product);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(ProductMapper.domainToResponse(product));
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(
            @PathVariable final UUID productId,
            @RequestBody final ChangePriceRequest request
    ) {
        final Product product = productUseCase.changePrice(ProductId.from(productId), BigDecimal.valueOf(request.getPrice()));
        final ProductResponse response = ProductMapper.domainToResponse(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        final List<ProductResponse> responses = productUseCase.findAll()
                .stream()
                .map(ProductMapper::domainToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
