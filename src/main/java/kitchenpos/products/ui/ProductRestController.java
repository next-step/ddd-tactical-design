package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.ui.dto.ProductRestMapper;
import kitchenpos.products.ui.dto.request.ProductChangePriceRestRequest;
import kitchenpos.products.ui.dto.request.ProductRestRequest;
import kitchenpos.products.ui.dto.response.ProductRestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<ProductRestResponse> create(@Valid @RequestBody final ProductRestRequest request) {
        final ProductResponse response = productService.create(ProductRestMapper.tDto(request));
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
                .body(ProductRestMapper.toRestDto(response));
    }

    @PutMapping("/{productId}/price")
    public ProductRestResponse changePrice(@PathVariable final UUID productId,
                                           @Valid @RequestBody final ProductChangePriceRestRequest request) {
        final ProductResponse response = productService.changePrice(productId, request.getPrice());
        return ProductRestMapper.toRestDto(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductRestResponse>> findAll() {
        List<ProductResponse> responses = productService.findAll();
        return ResponseEntity.ok(ProductRestMapper.toRestDtos(responses));
    }
}
