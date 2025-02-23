package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.*;

import java.util.Objects;

public class Product {
    private ProductId productId;
    private DisplayedName displayedName;
    private Price price;

    public Product(final Long productId, final String displayedName, final long price) {
        this(new ProductId(productId), displayedName, new EmptyProfanities(), price);
    }

    public Product(final ProductId productId, final String displayedName, final Profanities profanities, final long price) {
        this(productId, new DisplayedName(displayedName, profanities), new Price(price));
    }

    public Product(final ProductId productId, final DisplayedName displayedName, final Price price) {
        this.productId = productId;
        this.displayedName = displayedName;
        this.price = price;
    }

    public Long id() {
        return productId.getValue();
    }

    public String displayedName() {
        return displayedName.getValue();
    }

    public long price() {
        return price.getValue();
    }

    public Product changePrice(final long price) {
        return new Product(productId, displayedName, new Price(price));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product that = (Product) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}
