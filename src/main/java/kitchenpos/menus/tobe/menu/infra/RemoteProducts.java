package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.domain.MenuProduct;
import kitchenpos.menus.tobe.menu.domain.Products;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class RemoteProducts implements Products {

    private final ProductService productService;

    public RemoteProducts(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<MenuProduct> getMenuProductsByProductIdsAndQuantities(final List<Long> productIds, final List<Long> quantities) {
        if(productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("productIds가 입력되지 않았습니다.");
        }
        if(quantities == null || quantities.isEmpty()) {
            throw new IllegalArgumentException("quantities가 입력되지 않았습니다.");
        }
        if(productIds.size() != quantities.size()) {
            throw new IllegalArgumentException("productIds와 quantities는 같은 length를 가져야합니다.");
        }
        if (productIds.size() != productIds.parallelStream().distinct().count()) {
            throw new IllegalArgumentException("제품은 중복될 수 없습니다.");
        }

        final List<Product> products = productService.findAllById(productIds);
        return IntStream.range(0, products.size())
                .mapToObj(index -> new MenuProduct(products.get(index).getId(), products.get(index).getPrice(), quantities.get(index)))
                .collect(Collectors.toList());
    }
}
