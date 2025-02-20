package kitchenpos.products.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.products.application.service.ProductService;
import kitchenpos.products.application.dto.ChangeProductPriceRequestDto;
import kitchenpos.products.application.dto.ChangeProductPriceResponseDto;
import kitchenpos.products.application.dto.CreateProductRequestDto;
import kitchenpos.products.application.dto.CreateProductResponseDto;
import kitchenpos.products.application.dto.FindProductResponseDto;
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
    public ResponseEntity<CreateProductResponseDto> create(
        @RequestBody final CreateProductRequestDto request) {
        final CreateProductResponseDto response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.id()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ChangeProductPriceResponseDto> changePrice(
        @PathVariable final UUID productId,
        @RequestBody final ChangeProductPriceRequestDto request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<FindProductResponseDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
