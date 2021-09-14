package kitchenpos.menus.tobe.infra;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;
import kitchenpos.menus.tobe.domain.model.Quantity;
import kitchenpos.menus.tobe.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuProductsTranslator {

    private final ProductRepository productRepository;

    public MenuProductsTranslator(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MenuProducts getMenuProducts(final List<MenuProductRequest> menuProductRequests) {
        return new MenuProducts(menuProductRequests.stream()
            .map(menuProductRequest -> {
                final UUID productId = menuProductRequest.getProductId();
                final Product product = productRepository.findById(productId)
                    .orElseThrow(IllegalArgumentException::new);
                return new MenuProduct(new Quantity(menuProductRequest.getQuantity()), productId, product.getPrice());
            })
            .collect(Collectors.toList()));
    }

}
