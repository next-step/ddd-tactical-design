package kitchenpos.products.tobe.infra;

import kitchenpos.products.domain.Product;
import kitchenpos.products.tobe.application.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final Product request) {
        final Product response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
                .body(response);
    }
}
