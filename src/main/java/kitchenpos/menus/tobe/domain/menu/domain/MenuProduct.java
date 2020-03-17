package kitchenpos.menus.tobe.domain.menu.domain;

import kitchenpos.common.tobe.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Table(name = "menu_product")
@Access(AccessType.FIELD)
@Embeddable
public class MenuProduct {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    private Long productId;

    private long quantity;

    @Embedded
    private Price price;

    private MenuProduct() {
    }

    public MenuProduct(Long productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return price;
    }

    public BigDecimal calculateTest(Price price) {
        return price.multiply(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return quantity == that.quantity &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}
