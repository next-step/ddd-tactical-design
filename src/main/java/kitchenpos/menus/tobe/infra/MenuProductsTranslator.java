package kitchenpos.menus.tobe.infra;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.common.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;
import kitchenpos.menus.tobe.domain.model.Quantity;
import kitchenpos.menus.tobe.dto.MenuProductRequest;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
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
                return new MenuProduct(new Quantity(menuProductRequest.getQuantity()), productId, new Price(product.getPrice()));
            })
            .collect(Collectors.toList()));
    }

}
