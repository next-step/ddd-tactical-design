package kitchenpos.web;

import kitchenpos.core.products.application.CommandProductService;
import kitchenpos.core.products.application.QueryProductService;
import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.products.tobe.domain.Product;
import kitchenpos.core.shared.identifier.ProductId;
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

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final CommandProductService commandProductService;
    private final QueryProductService queryProductService;

    public ProductRestController(final CommandProductService commandProductService, QueryProductService queryProductService) {
        this.commandProductService = commandProductService;
        this.queryProductService = queryProductService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final CreateProductRequest request) {
        kitchenpos.core.products.tobe.domain.Product response = commandProductService.addProduct(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<Product> changePrice(@PathVariable final ProductId productId, @RequestBody final Product request) {
        return ResponseEntity.ok(commandProductService.changePrice(productId, request.getPrice()));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(queryProductService.findProducts());
    }
}
