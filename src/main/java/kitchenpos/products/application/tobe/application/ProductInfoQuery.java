package kitchenpos.products.application.tobe.application;

import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/*
MenuService에서 productRepository를 직접 호출하지 않기 위해서
만든 ACL역할을 하는 파일입니다.

MenuService에서 product 정보 시에는 이 파일 통해서 조회합니다.
*/

@Component
public class ProductInfoQuery {

    private final ProductRepository productRepository;

    public ProductInfoQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int size(List<ProductId> productIds) {
        return productRepository.findAllByIdIn(productIds).size();
    }

    public Price calculateTotalPrice(List<ProductId> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);
        return products.stream()
                .map(Product::getPrice)
                .reduce(Price.ZERO(), Price::add);
    }
}
