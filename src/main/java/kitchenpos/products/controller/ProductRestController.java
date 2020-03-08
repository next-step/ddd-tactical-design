package kitchenpos.products.controller;

import java.util.stream.Collectors;
import kitchenpos.products.bo.ProductBo;
import kitchenpos.products.model.ProductRequest;
import kitchenpos.products.model.ProductView;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class ProductRestController {
    private final ProductBo productBo;

    public ProductRestController(final ProductBo productBo) {
        this.productBo = productBo;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductView> create(@RequestBody final ProductRequest productRequest) {
        final Product created = productBo.create(productRequest);
        final URI uri = URI.create("/api/products/" + created.getId());
        return ResponseEntity.created(uri)
                .body(map(created));
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductView>> list() {
        return ResponseEntity.ok()
            .body(productBo.list().stream().map(this::map).collect(Collectors.toList()));
    }

    private ProductView map(Product product){
        return ProductView.builder()
            .withId(product.getId())
            .withPrice(product.getPrice().getValue())
            .withName(product.getName())
            .build();
    }
}
