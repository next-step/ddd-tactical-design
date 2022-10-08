package kitchenpos.products.tobe.domain;

public class Product {
    private final ProductPrice price;
    private final DisplayedName name;

    public Product(final ProductPrice price, final DisplayedName name) {
        this.price = price;
        this.name = name;
    }
}
