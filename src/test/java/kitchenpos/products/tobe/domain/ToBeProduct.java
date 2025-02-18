package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ToBeProduct {
    private final UUID productId;
    private final ToBeDisplayedName displayedName;
    private final ToBePrice price;

    public ToBeProduct(final UUID productId, final String displayedName, final BigDecimal price) {
        this(productId, new ToBeDisplayedName(displayedName), new ToBePrice(price));
    }

    public ToBeProduct(final UUID productId, final ToBeDisplayedName displayedName, final ToBePrice price) {
        this.productId = productId;
        this.displayedName = displayedName;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public ToBeDisplayedName getDisplayedName() {
        return displayedName;
    }

    public ToBePrice getPrice() {
        return price;
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
