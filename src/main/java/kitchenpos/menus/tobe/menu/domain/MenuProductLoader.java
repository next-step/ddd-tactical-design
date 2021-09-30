package kitchenpos.menus.tobe.menu.domain;

import kitchenpos.menus.tobe.menu.ui.dto.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class MenuProductLoader {
    public MenuProducts loadMenuProducts(final List<MenuProduct> menuProducts, final List<ProductResponse> products) {
        validateRegistered(menuProducts, products);
        products.forEach(product -> loadProduct(menuProducts, product));
        return new MenuProducts(menuProducts);
    }

    private void validateRegistered(final List<MenuProduct> menuProducts, final List<ProductResponse> products) {
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException("상품이 등록되어있지 않습니다.");
        }
    }

    private void loadProduct(final List<MenuProduct> menuProducts, final ProductResponse product) {
        menuProducts.stream()
                .filter(menuProduct -> Objects.equals(menuProduct.getProductId(), product.getId()))
                .forEach(menuProduct -> menuProduct.loadPrice(product));
    }
}
