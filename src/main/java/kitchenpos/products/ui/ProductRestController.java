package kitchenpos.products.ui;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.application.dto.ProductInfo;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.ui.request.ProductChangeRequest;
import kitchenpos.products.ui.request.ProductCreateRequest;
import kitchenpos.products.ui.response.ProductChangeResponse;
import kitchenpos.products.ui.response.ProductCreateResponse;
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
        final ProductInfo product = productService.create(request);

        return ResponseEntity.created(URI.create("/api/products/" + product.getId()))
            .body(new ProductCreateResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice()
                )
            );
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductChangeResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ProductChangeRequest request) {
        final ProductInfo productInfo = productService.changePrice(productId, request);

        return ResponseEntity.ok(
                new ProductChangeResponse(
                        productInfo.getId(),
                        productInfo.getName(),
                        productInfo.getPrice()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductInfo>> findAll() {
        final List<Product> product = productService.findAll();

        return ResponseEntity.ok(
                product.stream()
                        .map(it -> new ProductInfo(
                                it.getId(),
                                it.getName().getProductName(),
                                it.getPrice().getProductPrice()))
                        .collect(Collectors.toList())
        );
    }
}
