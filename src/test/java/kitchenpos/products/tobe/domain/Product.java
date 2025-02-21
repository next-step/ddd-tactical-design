package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;
import kitchenpos.products.tobe.domain.vo.ProfanityName;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long productId;
    private DisplayedName displayedName;
    private Price price;

    public Product(final Long productId, final String displayedName, final Long price) {
        this(productId, displayedName, new ProfanityName(), price);
    }

    public Product(final Long productId, final String displayedName, final ProfanityName profanityName, final long price) {
        this(productId, new DisplayedName(displayedName, profanityName), new Price(price));
    }

    public Product(final Long productId, final DisplayedName displayedName, final Price price) {
        this.productId = productId;
        this.displayedName = displayedName;
        this.price = price;
    }

    public Long getId() {
        return productId;
    }

    public String getName() {
        return displayedName.getDisplayedName();
    }

    public long getPrice() {
        return price.getPrice();
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
