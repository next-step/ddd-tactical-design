package kitchenpos.menus.tobe.menu.infra;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.menu.domain.model.MenuProduct;
import kitchenpos.menus.tobe.menu.domain.model.MenuProducts;
import kitchenpos.menus.tobe.menu.domain.model.Quantity;
import kitchenpos.menus.tobe.menu.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuProductsMapper {

    private final ProductRepository productRepository;

    public MenuProductsMapper(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MenuProducts toMenuProducts(final List<MenuProductRequest> menuProductRequests) {
        final List<UUID> menuProductIds = menuProductRequests.stream()
            .map(MenuProductRequest::getProductId)
            .collect(Collectors.toList());
        final List<Product> products = productRepository.findAllByIdIn(menuProductIds);
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException("모든 상품이 등록되어 있어야 합니다");
        }
        return new MenuProducts(menuProductRequests.stream()
            .map(menuProductRequest -> {
                final UUID productId = menuProductRequest.getProductId();
                final Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException("해당 상품을 찾을 수 없습니다."));
                return new MenuProduct(new Quantity(menuProductRequest.getQuantity()), productId, product.getPrice());
            })
            .collect(Collectors.toList()));
    }

}
