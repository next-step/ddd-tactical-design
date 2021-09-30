package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.application.MenuProductClient;
import kitchenpos.menus.tobe.domain.ProductVO;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class RestMenuProductClient implements MenuProductClient {

    private final ProductService productService; // 원래라면 HTTP client를 활용하여 product의 값을 얻어오는 방식

    public RestMenuProductClient(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        return productService.findAllByIdIn(productIds);
    }

    @Override
    public Product findById(UUID productId) {
        return productService.findById(productId);
    }

    @Override
    public List<ProductVO> findAllByIdn(List<UUID> productIds) {
        return productService.findAllByIdIn(productIds)
                .stream()
                .map(product -> new ProductVO(product.getId(), product.getPrice()))
                .collect(toList());
    }
}
