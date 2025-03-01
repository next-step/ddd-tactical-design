package kitchenpos.tobe.product.presesntation;

import kitchenpos.tobe.product.application.ProductService;
import kitchenpos.tobe.product.application.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class PostRestController {

    private final ProductService productService;

    public PostRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> create(@RequestBody final CreateProductRequest request) {
        final CreateProductResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.id()))
                .body(response);
    }

    @PutMapping("/{productId}/price")
    public ChangeProductPriceResponse changePrice(@PathVariable final UUID productId,
                                                  @RequestBody final ChangeProductPriceRequest request) {
        return productService.changePrice(productId, request);
    }

    @GetMapping
    public List<ProductListResponse> findAll() {
        return productService.findAll();
    }

}
