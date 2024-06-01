package kitchenpos.products.tobe.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.products.tobe.application.ProductQueryHandler;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.dto.ProductCreateDto;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import kitchenpos.products.tobe.ui.view.ProductViewModel;
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
    private final ProductQueryHandler productQueryHandler;

    public ProductRestController(ProductService productService, ProductQueryHandler productQueryHandler) {
        this.productService = productService;
        this.productQueryHandler = productQueryHandler;
    }

    @PostMapping
    public ResponseEntity<ProductViewModel> create(@RequestBody final ProductCreateDto request) {
        final ProductViewModel response = ProductViewModel.from(productService.create(request));
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductViewModel> changePrice(@PathVariable final UUID productId, @RequestBody final ProductPriceChangeDto request) {
        final ProductViewModel response = ProductViewModel.from(productService.changePrice(productId, request));
        System.out.println("###### " + response);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductViewModel>> findAll() {
        List<Product> products = productQueryHandler.findAll();
        List<ProductViewModel> response = products.stream().map(ProductViewModel::from).toList();
        response.forEach(it -> System.out.println("###### " + it) );
        return ResponseEntity.ok(response);
    }
}
