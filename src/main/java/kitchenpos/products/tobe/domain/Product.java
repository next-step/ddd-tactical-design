package kitchenpos.products.tobe.domain;

public class Product {
    private ProductPrice price;
    private DisplayedName name;

    public Product(final ProductPrice price, final DisplayedName name) {
        this.price = price;
        this.name = name;
    }

    public void changePrice(final ProductPrice price) {
        this.price = price;
    }
}
