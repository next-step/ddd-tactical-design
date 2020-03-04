package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private ProductPrice productPrice;

    private Product(final String name, final int price) {
        this.name = name;
        this.productPrice = ProductPrice.valueOf(price);
    }

    public static Product registerProduct(final String name, final int price) {
        return new Product(name, price);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return productPrice.checkProductPriceValue();
    }
}
