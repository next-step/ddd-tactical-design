package kitchenpos.products.tobe.domain;

public final class ProductPriceChangedEvent {
    private final Product product;

    public ProductPriceChangedEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
