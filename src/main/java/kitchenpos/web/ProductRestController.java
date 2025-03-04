package kitchenpos.web;

import kitchenpos.core.products.application.*;
import kitchenpos.core.products.application.dto.CreateProductRequest;
import kitchenpos.core.products.tobe.domain.Product;
import kitchenpos.core.products.tobe.domain.ProductPrice;
import kitchenpos.core.shared.identifier.ProductId;
import kitchenpos.core.shared.value.Money;
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

@RequestMapping("/v1/api/products")
@RestController
public class ProductRestController {
    private final AddProduct addProduct;
    private final ChangeProductPrice changeProductPrice;
    private final FindProducts findProducts;

    public ProductRestController(AddProduct addProduct, ChangeProductPrice changeProductPrice, FindProducts findProducts) {
        this.addProduct = addProduct;
        this.changeProductPrice = changeProductPrice;
        this.findProducts = findProducts;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody final CreateProductRequest request) {
        kitchenpos.core.products.tobe.domain.Product response = addProduct.addProduct(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<Product> changePrice(@PathVariable final UUID productId, @RequestBody final Long amount) {
        return ResponseEntity.ok(changeProductPrice.changePrice(ProductId.of(productId), ProductPrice.of(Money.wons(amount))));
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(findProducts.findProducts());
    }
}
