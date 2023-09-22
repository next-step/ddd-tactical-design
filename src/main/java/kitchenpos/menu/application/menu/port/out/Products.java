package kitchenpos.menu.application.menu.port.out;

import java.util.List;

public final class Products {

    private final List<Product> values;

    public Products(final List<Product> values) {
        this.values = values;
    }

    public List<Product> getValues() {
        return values;
    }
}
