package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.domain.Products;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class RemoteProducts implements Products {

    private final ProductService productService;

    public RemoteProducts(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> getProductsByProductIds(final List<Long> productIds) {
        if (CollectionUtils.isEmpty(productIds)) {
            throw new IllegalArgumentException("productIds 가 입력되지 않았습니다.");
        }
        if (productIds.size() != productIds.parallelStream().distinct().count()) {
            throw new IllegalArgumentException("제품은 중복될 수 없습니다.");
        }

        return productService.findAllById(productIds);
    }
}
