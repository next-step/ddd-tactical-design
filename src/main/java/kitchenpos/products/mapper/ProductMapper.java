package kitchenpos.products.mapper;

import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse(
                product.getId().getValue()
                , product.getName()
                , product.getPrice());
        return productResponse;
    }
}
