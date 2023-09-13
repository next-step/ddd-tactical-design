package kitchenpos.menus.domain.tobe.domain;

import java.util.List;
import java.util.stream.Collectors;

import kitchenpos.common.DomainService;
import kitchenpos.menus.domain.Menu;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;
import kitchenpos.products.domain.tobe.domain.ToBeProductRepository;

@DomainService
public class ProductFinder {
    private final ToBeProductRepository productRepository;

    public ProductFinder(ToBeProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ToBeMenuProducts find(final Menu request) {
        List<ToBeMenuProduct> menuProductList = request.getMenuProducts().stream()
            .map(it -> {
                ToBeProduct product = productRepository.findById(it.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 없으면 등록할 수 없다."));
                return new ToBeMenuProduct(product.getId(), product.getPrice().getValue(), it.getQuantity());
            })
            .collect(Collectors.toList());
        return new ToBeMenuProducts(menuProductList);
    }
}
