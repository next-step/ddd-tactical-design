package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import kitchenpos.common.model.Price;

@Embeddable
@Access(AccessType.FIELD)
public class MenuProduct {

    private long productId;
    private long quantity;

    @Transient
    private Price productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(Long productId, long quantity, Price productPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public MenuProduct(Long productId, long quantity, BigDecimal productPrice) {
        this(productId, quantity, Price.of(productPrice));
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    Price computePriceSum() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return quantity == that.quantity &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}
