package kitchenpos.products.tobe.ui;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.ui.dto.ChangeProductRequest;
import kitchenpos.products.tobe.ui.dto.ChangeProductResponse;
import kitchenpos.products.tobe.ui.dto.CreateProductRequest;
import kitchenpos.products.tobe.ui.dto.CreateProductResponse;
import kitchenpos.products.tobe.ui.dto.FindProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CreateProductResponse> create(
            @RequestBody final CreateProductRequest request) {
        final CreateProductResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.id()))
                .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ChangeProductResponse> changePrice(
            @PathVariable final UUID productId,
            @RequestBody final ChangeProductRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<FindProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
