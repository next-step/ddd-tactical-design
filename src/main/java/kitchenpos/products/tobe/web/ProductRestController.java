package kitchenpos.products.tobe.web;

import java.net.URI;
import java.util.List;
import kitchenpos.products.tobe.domain.usecase.ProductService;
import kitchenpos.products.tobe.web.dto.ProductResponseDto;
import kitchenpos.products.tobe.web.dto.ProductSaveRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponseDto> create(
        @RequestBody ProductSaveRequestDto requestDto) {
        final ProductResponseDto created = productService.create(requestDto);
        final URI uri = URI.create("/api/products/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponseDto>> list() {
        return ResponseEntity.ok().body(productService.list());
    }
}
