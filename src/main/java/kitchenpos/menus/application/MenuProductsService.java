package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.domain.tobe.menu.MenuPrice;
import kitchenpos.menus.domain.tobe.menuproduct.MenuProduct;
import kitchenpos.menus.domain.tobe.menuproduct.MenuProducts;
import kitchenpos.menus.ui.dto.MenuProductCreateRequests;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Service;

@Service
public class MenuProductsService {

    private final ProductRepository productRepository;

    public MenuProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MenuProducts create(final MenuProductCreateRequests requests,
            MenuPrice price) {
        List<Product> products = findProducts(requests.getProductIds());
        requests.validateMenuProducts(products);

        return new MenuProducts(createMenuProducts(requests), price);
    }

    private List<Product> findProducts(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }

    private List<MenuProduct> createMenuProducts(MenuProductCreateRequests requests) {
        return requests.getMenuProducts()
                .stream()
                .map(request -> {
                    Product product = findProduct(request.getProductId());
                    return request.to(product);
                })
                .collect(Collectors.toList());
    }

    private Product findProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }
}
