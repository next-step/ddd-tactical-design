package kitchenpos.products.controller;

import kitchenpos.products.bo.ProductBo;
import kitchenpos.products.model.ProductData;
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
    public ResponseEntity<ProductData> create(@RequestBody final ProductData productData) {
        final ProductData created = productBo.create(productData);
        final URI uri = URI.create("/api/products/" + created.getId());
        return ResponseEntity.created(uri)
                .body(created)
                ;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductData>> list() {
        return ResponseEntity.ok()
                .body(productBo.list())
                ;
    }
}
