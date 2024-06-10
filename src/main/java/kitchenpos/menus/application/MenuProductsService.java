package kitchenpos.menus.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.domain.tobe.menu.MenuPrice;
import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
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

        List<MenuProduct> menuProducts = createMenuProducts(requests);
        validateMenuPrice(menuProducts, price);
        return new MenuProducts(menuProducts);
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

    public void validateMenuPrice(List<MenuProduct> menuProducts, MenuPrice price) {
        if (isOverThanProductSumPrice(menuProducts, price)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isOverThanProductSumPrice(List<MenuProduct> menuProducts, MenuPrice price) {
        return price.isOver(calculateProductSumPrice(menuProducts));
    }

    private BigDecimal calculateProductSumPrice(List<MenuProduct> menuProducts) {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            Product product = findProduct(menuProduct.getProductId());
            sum = sum.add(menuProduct.calculateSum(product));
        }
        return sum;
    }

    private List<Product> findProducts(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }

    private Product findProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }
}
