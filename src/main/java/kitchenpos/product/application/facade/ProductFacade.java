package kitchenpos.product.application.facade;

import java.util.List;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.dto.ProductResponse;
import kitchenpos.product.domain.service.ProductCommandService;
import kitchenpos.product.domain.service.ProductQueryService;
import org.springframework.stereotype.Component;

@Component
public class ProductFacade {

    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    public ProductFacade(
        ProductQueryService productQueryService,
        ProductCommandService productCommandService
    ) {
        this.productQueryService = productQueryService;
        this.productCommandService = productCommandService;
    }

    public ProductResponse.GetProduct create(ProductRequest.Create request) {
        return ProductResponse.GetProduct.fromVo(productCommandService.create(request.toVo()));
    }

    public ProductResponse.GetProduct changePrice(ProductRequest.UpdatePrice request) {
        return ProductResponse.GetProduct.fromVo(productCommandService.changePrice(request.toVo()));
    }

    public List<ProductResponse.GetProduct> findAll() {
       return productQueryService.findAll()
           .stream()
           .map(ProductResponse.GetProduct::fromVo)
           .toList();
    }
}
