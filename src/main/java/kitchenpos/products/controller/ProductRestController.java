package kitchenpos.products.controller;

import kitchenpos.products.bo.ProductBo;
import kitchenpos.products.dto.ProductDto;
import kitchenpos.products.tobe.domain.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductRestController {
    private final ProductBo productBo;

    public ProductRestController(final ProductBo productBo) {
        this.productBo = productBo;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductDto> create(@RequestBody final ProductDto productDto) {
        final Product created = productBo.create(productDto.toProduct());
        final URI uri = URI.create("/api/products/" + created.getId());
        return ResponseEntity.created(uri)
                .body(new ProductDto(created.getId(), created.getName(), created.getPrice().getPrice()))
                ;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductDto>> list() {
        return ResponseEntity.ok()
                .body(productBo.list().stream()
                        .map(item -> {
                            return new ProductDto(item.getId(), item.getName(), item.getPrice().getPrice());
                        })
                        .collect(Collectors.toList()))
                ;
    }
}
