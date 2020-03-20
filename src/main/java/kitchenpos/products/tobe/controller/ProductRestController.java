package kitchenpos.products.tobe.controller;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.dto.ProductRequest;
import kitchenpos.products.tobe.domain.dto.ProductResponse;
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
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> create(@RequestBody final ProductRequest request) {
        final Product requestProduct = request.toProduct();
        final Product product = productService.create(requestProduct);
        final URI uri = URI.create("/api/products/" + product.getId());

        return ResponseEntity.created(uri)
                .body(new ProductResponse(product));
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> list() {
        List<Product> productList = productService.list();
        List<ProductResponse> responseList = productList.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(responseList)
                ;
    }
}
