package kitchenpos.product.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.product.application.ProductService;
import kitchenpos.product.application.dto.ChangeProductPriceServiceRq;
import kitchenpos.product.application.dto.CreateProductServiceRq;
import kitchenpos.product.application.dto.ProductServiceRs;
import kitchenpos.product.ui.dto.ChangeProductPriceRq;
import kitchenpos.product.ui.dto.CreateProductRq;
import kitchenpos.product.ui.dto.ProductRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductRs> create(@RequestBody final CreateProductRq request) {
        ProductServiceRs response = productService.create(
                new CreateProductServiceRq(request.getName(), request.getPrice()));
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
                .body(new ProductRs(response));
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductRs> changePrice(@PathVariable("productId") final UUID productId,
                                                 @RequestBody final ChangeProductPriceRq request) {
        ProductServiceRs response = productService.changePrice(
                productId,
                new ChangeProductPriceServiceRq(request.getPrice())
        );
        return ResponseEntity.ok(new ProductRs(response));
    }

    @GetMapping
    public ResponseEntity<List<ProductRs>> findAll() {
        List<ProductServiceRs> response = productService.findAll();
        return ResponseEntity.ok(
                response.stream()
                        .map(ProductRs::new)
                        .toList()
        );
    }
}
