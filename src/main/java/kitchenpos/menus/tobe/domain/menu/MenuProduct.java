package kitchenpos.menus.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import kitchenpos.common.model.Price;

@Embeddable
@Access(AccessType.FIELD)
public class MenuProduct {

    private ProductQuantity productQuantity;

    @Transient
    private Price productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(Long productId, long quantity, BigDecimal productPrice) {
        this(ProductQuantity.of(productId, quantity), Price.of(productPrice));
    }

    public MenuProduct(ProductQuantity productQuantity, Price productPrice) {
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public Long getProductId() {
        return productQuantity.getProductId();
    }

    public long getQuantity() {
        return productQuantity.getQuantity();
    }

    Price computePriceSum() {
        return productPrice.multiply(BigDecimal.valueOf(productQuantity.getQuantity()));
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
        return Objects.equals(productQuantity, that.productQuantity) &&
            Objects.equals(productPrice, that.productPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productQuantity, productPrice);
    }
}
