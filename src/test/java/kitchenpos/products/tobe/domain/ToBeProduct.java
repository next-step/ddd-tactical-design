package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.Product;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ToBeProduct extends Product {
    private UUID productId;
    private ToBeDisplayedName displayedName;
    private ToBePrice price;

    public ToBeProduct(final UUID productId, final String displayedName, final Long price) {
        this(productId, displayedName, BigDecimal.valueOf(price));
    }

    public ToBeProduct(final UUID productId, final String displayedName, final BigDecimal price) {
        this(productId, new ToBeDisplayedName(displayedName, new ProfanityName()), new ToBePrice(price));
    }

    public ToBeProduct(final UUID uuid, final String displayedName, final ProfanityName profanityName, final BigDecimal bigDecimal) {
        this(uuid, new ToBeDisplayedName(displayedName, profanityName), new ToBePrice(bigDecimal));
    }

    public ToBeProduct(final UUID productId, final ToBeDisplayedName displayedName, final ToBePrice price) {
        this.productId = productId;
        this.displayedName = displayedName;
        this.price = price;
    }

    @Override
    public UUID getId() {
        return productId;
    }

    @Override
    public String getName() {
        return displayedName.getDisplayedName();
    }

    @Override
    public BigDecimal getPrice() {
        return price.getPrice();
    }

    @Override
    public void setPrice(final BigDecimal price) {
        this.price = new ToBePrice(price);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ToBeProduct that = (ToBeProduct) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}
