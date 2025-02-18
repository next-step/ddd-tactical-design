package kitchenpos.product.presentation.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.dto.ProductRequest.UpdatePrice;
import kitchenpos.product.application.dto.ProductResponse;
import kitchenpos.product.application.facade.ProductFacade;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.service.ProductService;
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

    private final ProductFacade productFacade;

    public ProductRestController(final ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(
        @RequestBody final ProductRequest.Create request
    ) {
        final ProductResponse response = productFacade.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.id()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(
        @PathVariable final UUID productId,
        @RequestBody final ProductRequest.UpdatePrice request
    ) {
        return ResponseEntity.ok(productFacade.changePrice(new UpdatePrice(productId, request.price())));
    }

    @GetMapping
    public ResponseEntity<List<ProductRequest>> findAll() {
        return ResponseEntity.ok(productFacade.findAll());
    }
}
