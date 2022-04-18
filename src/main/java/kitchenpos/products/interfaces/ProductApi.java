package kitchenpos.products.interfaces;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import kitchenpos.products.application.ProductFacade;
import kitchenpos.products.domain.dtos.ProductCommand.ChangePriceCommand;
import kitchenpos.products.domain.dtos.ProductCommand.RegisterProductCommand;
import kitchenpos.products.domain.dtos.ProductInfo;
import kitchenpos.products.interfaces.dtos.ProductDto;
import kitchenpos.products.interfaces.dtos.ProductDto.ProductResponse;
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
public class ProductApi {
    private final ProductFacade productFacade;

    public ProductApi(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid final ProductDto.RegisterProductRequest request) {
        final RegisterProductCommand command = request.toCommand();
        final ProductInfo productInfo = productFacade.createProduct(command);
        final ProductResponse response = new ProductResponse(productInfo);

        return ResponseEntity.created(URI.create("/api/products/" + productInfo.getId()))
                .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable @Valid final UUID productId, @RequestBody @Valid final ProductDto.ChangePriceRequest request) {
        final ChangePriceCommand command = request.toCommand();
        final ProductInfo productInfo = productFacade.changePrice(productId, command);
        final ProductResponse response = new ProductResponse(productInfo);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        final List<ProductInfo> productInfos = productFacade.findAll();
        final List<ProductResponse> responses = productInfos.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}
