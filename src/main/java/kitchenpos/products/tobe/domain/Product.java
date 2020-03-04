package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;

    private Product(final String name, final int price) {
        validatePrice(price);
        this.name = name;
        this.price = BigDecimal.valueOf(price);
    }

    private void validatePrice(final int price) {
        if (price < 0) throw new IllegalArgumentException();
    }

    public static Product registerProduct(final String name, final int price) {
        return new Product(name, price);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
