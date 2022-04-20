package kitchenpos.menus.tobe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kitchenpos.products.domain.Product;

public class MenuProducts {
    private List<Product> products = new ArrayList<>();

    public MenuProducts(List<Product> products) {
        validateProducts(products);
        this.products = products;
    }

    private void validateProducts(List<Product> products) {
        if (Objects.isNull(products) || products.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
