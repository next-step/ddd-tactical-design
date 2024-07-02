package kitchenpos.menus.domain.tobe;

import java.util.List;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Component;

@Component
public class MenuProductValidator {

    public void validate(List<MenuProduct> menuProducts, List<Product> products) {
        if (menuProducts == null || products == null) {
            throw new IllegalArgumentException();
        }

        if (menuProducts.isEmpty() || products.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }
    }
}
