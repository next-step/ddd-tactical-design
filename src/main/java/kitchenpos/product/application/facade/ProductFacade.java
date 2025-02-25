package kitchenpos.product.application.facade;

import java.util.List;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.dto.ProductResponse;
import kitchenpos.product.domain.service.ProductCommandService;
import kitchenpos.product.domain.service.ProductQueryService;
import org.springframework.stereotype.Component;

@Component
public class ProductFacade {

    private final ProductQueryService productQuryService;
    private final ProductCommandService productCommandService;

    public ProductFacade(
        ProductQueryService productQuryService,
        ProductCommandService productCommandService
    ) {
        this.productQuryService = productQuryService;
        this.productCommandService = productCommandService;
    }

    public ProductResponse.GetProduct create(ProductRequest.Create request) {
        return ProductResponse.GetProduct.fromVo(productCommandService.create(request.toVo()));
    }

    public ProductResponse.GetProduct changePrice(ProductRequest.UpdatePrice request) {
        return ProductResponse.GetProduct.fromVo(productCommandService.changePrice(request.toVo()));
    }

    public List<ProductResponse.GetProduct> findAll() {
       return productQuryService.findAll()
           .stream()
           .map(ProductResponse.GetProduct::fromVo)
           .toList();
    }
}
