package kitchenpos.menu.domain.service;

import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuProductValidator {
    private static final String MENU_PRODUCT_VALIDATION_EXCEPTION = "메뉴 상품에 들어갈 상품 수와 실제 상품 수가 다릅니다!";

    private final ProductRepository productRepository;

    public MenuProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateMenuProduct(List<MenuProduct> menuProducts) {
        final List<Product> products = productRepository.findAllByIdIn(
                menuProducts.stream()
                        .map(MenuProduct::getProductId)
                        .toList()
        );
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException(MENU_PRODUCT_VALIDATION_EXCEPTION);
        }
    }
}
