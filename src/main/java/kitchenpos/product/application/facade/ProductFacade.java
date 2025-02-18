package kitchenpos.product.application.facade;

import java.util.List;
import java.util.UUID;
import kitchenpos.product.application.dto.ProductRequest;
import kitchenpos.product.application.dto.ProductResponse;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.service.ProductPurgomalumClient;
import kitchenpos.product.domain.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductFacade {

    private final ProductService productService;

    public ProductFacade(
        ProductService productService
    ) {
        this.productService = productService;
    }

    public ProductResponse.GetProduct create(ProductRequest.Create request) {
        return ProductResponse.GetProduct.fromVo(productService.create(request.toVo()));
    }

    public ProductResponse.GetProduct changePrice(ProductRequest.UpdatePrice request) {
        return ProductResponse.GetProduct.fromVo(productService.changePrice(request.toVo()));
    }

    public List<ProductResponse.GetProduct> findAll() {
       return productService.findAll()
           .stream()
           .map(ProductResponse.GetProduct::fromVo)
           .toList();
    }
}
