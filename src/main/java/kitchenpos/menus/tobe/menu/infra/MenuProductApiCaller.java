package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.domain.MenuProductRepository;
import kitchenpos.menus.tobe.menu.domain.Name;
import kitchenpos.menus.tobe.menu.domain.product.Price;
import kitchenpos.menus.tobe.menu.domain.product.Product;
import kitchenpos.menus.tobe.menu.ui.dto.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class MenuProductApiCaller implements MenuProductRepository {

    private final kitchenpos.products.tobe.application.ProductService productService; //msa라는 가정, api call

    public MenuProductApiCaller(final kitchenpos.products.tobe.application.ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        return productService.findAllByIdIn(productIds).stream()
                .map(product -> new Product(new Name(product.getName().value()), new Price(product.getPrice().value())))
                .collect(toList());
    }

    @Override
    public Product findById(UUID productId) {
        kitchenpos.products.tobe.domain.Product product = productService.findById(productId);
        return new Product(product.getId(), new Name(product.getName().value()), new Price(product.getPrice().value()));
    }

    @Override
    public List<ProductResponse> findAllByIdn(List<UUID> productIds) {
        return productService.findAllByIdIn(productIds)
                .stream()
                .map(product -> new ProductResponse(product.getId(), new Price(product.getPrice().value())))
                .collect(toList());
    }
}
