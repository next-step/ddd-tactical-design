package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.domain.MenuProductRepository;
import kitchenpos.menus.tobe.menu.domain.product.Price;
import kitchenpos.menus.tobe.menu.ui.dto.ProductResponse;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class MenuProductApiCaller implements MenuProductRepository {

    private final ProductService productService; //msa라는 가정으로 api call

    public MenuProductApiCaller(final ProductService productService) {
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
    public List<ProductResponse> findAllByIdn(List<UUID> productIds) {
        return productService.findAllByIdIn(productIds)
                .stream()
                .map(product -> new ProductResponse(product.getId(), new Price(product.getPrice().getPriceValue())))
                .collect(toList());
    }
}
