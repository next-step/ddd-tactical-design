package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.application.MenuProductClient;
import kitchenpos.menus.tobe.menu.ui.dto.ProductResponse;
import kitchenpos.products.tobe.application.ProductService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class RestMenuProductClient implements MenuProductClient {

    private final ProductService productService; // 원래라면 HTTP client를 활용하여 product의 값을 얻어오는 방식

    public RestMenuProductClient(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<ProductResponse> findAllByIdn(List<UUID> productIds) {
        return productService.findAllByIdIn(productIds)
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getPrice()))
                .collect(toList());
    }
}
