package kitchenpos.products.ui;

import kitchenpos.products.application.TobeProductService;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;
import kitchenpos.products.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/Tobeproducts")
@RestController
public class TobeProductRestController {
    private final TobeProductService productService;

    public TobeProductRestController(final TobeProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductRegisterResponse> create(@RequestBody final ProductRegisterRequest request) {
        final TobeProduct product = productService.create(request);
        final ProductRegisterResponse response = new ProductRegisterResponse(product);
        return ResponseEntity.created(URI.create("/api/products/" + response.getProductId().getValue()))
                .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductPriceChangeResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ProductPriceChangeRequest request) {
        request.setProductId(new ProductId(productId));
        final TobeProduct product = productService.changePrice(request);
        final ProductPriceChangeResponse response = new ProductPriceChangeResponse(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(productService.findAll().stream().map(ProductDto::new).collect(Collectors.toList()));
    }
}
