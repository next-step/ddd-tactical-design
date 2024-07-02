package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.application.dto.MenuProductCreateRequest;
import kitchenpos.menus.application.dto.MenuProductsCreateRequest;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuProductValidator;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.menus.domain.tobe.ProductQuantity;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Component;

@Component
public class MenuProductMapper {

    private final ProductRepository productRepository;
    private final MenuProductValidator menuProductValidator;

    public MenuProductMapper(ProductRepository productRepository,
        MenuProductValidator menuProductValidator) {
        this.productRepository = productRepository;
        this.menuProductValidator = menuProductValidator;
    }

    public MenuProducts map(MenuProductsCreateRequest request) {
        List<UUID> productIds = request.menuProducts()
            .stream().map(MenuProductCreateRequest::productId)
            .toList();

        List<Product> products = productRepository.findAllByIdIn(productIds);
        List<MenuProduct> menuProducts = createMenuProducts(request, products);
        menuProductValidator.validate(menuProducts, products);
        return new MenuProducts(menuProducts);
    }

    private List<MenuProduct> createMenuProducts(MenuProductsCreateRequest menuProducts,
        List<Product> products) {
        return menuProducts.menuProducts()
            .stream()
            .map(menuProduct -> createMenuProduct(products,
                menuProduct.productId(),
                menuProduct.quantity())
            )
            .toList();
    }

    private MenuProduct createMenuProduct(List<Product> products,
        UUID productId,
        ProductQuantity quantity) {
        Product product = findProduct(products, productId);
        return new MenuProduct(product, quantity);
    }

    private Product findProduct(List<Product> products, UUID productId) {
        return products.stream()
            .filter(product -> product.getId().equals(productId))
            .findFirst().orElseThrow((NoSuchElementException::new));
    }
}
