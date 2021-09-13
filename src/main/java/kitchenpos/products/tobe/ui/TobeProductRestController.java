package kitchenpos.products.tobe.ui;

import kitchenpos.products.tobe.application.TobeProductService;
import kitchenpos.products.tobe.domain.TobeProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/tobe/api/products")
@RestController
public class TobeProductRestController {
    private final TobeProductService productService;

    public TobeProductRestController(final TobeProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductForm> create(@RequestBody final ProductForm request) {
        final TobeProduct response = productService.create(request);

        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(ProductForm.of(response));
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductForm> changePrice(@PathVariable final UUID productId, @RequestBody final ProductForm request) {
        TobeProduct response = productService.changePrice(productId, request);

        return ResponseEntity.ok(ProductForm.of(response));
    }

    @GetMapping
    public ResponseEntity<List<ProductForm>> findAll() {
        List<TobeProduct> products = productService.findAll();

        List<ProductForm> response = products.stream()
                .map(product -> ProductForm.of(product))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
