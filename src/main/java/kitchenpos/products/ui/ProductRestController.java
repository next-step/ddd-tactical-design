package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.ui.request.ProductCreateRequest;
import kitchenpos.products.ui.request.ProductModifyRequest;
import kitchenpos.products.ui.response.ProductCreateResponse;
import kitchenpos.products.ui.response.ProductDto;
import kitchenpos.products.ui.response.ProductModifyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponse> create(@RequestBody final ProductCreateRequest request) {
        final Product product = productService.create(request);
        ProductCreateResponse response = new ProductCreateResponse(product.getId(), product.getName(), product.getPrice());
        return ResponseEntity.created(URI.create("/api/products/" + product.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductModifyResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ProductModifyRequest request) {
        Product product = productService.changePrice(productId, request);
        ProductModifyResponse response = new ProductModifyResponse(product.getId(), product.getName(), product.getPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        List<Product> products = productService.findAll();
        List<ProductDto> response = products.stream()
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
