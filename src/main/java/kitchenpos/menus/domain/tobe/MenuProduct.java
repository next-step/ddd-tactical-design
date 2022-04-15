package kitchenpos.menus.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private MenuQuantity quantity;

    private UUID productId;

    @Transient
    private MenuPrice price;

    protected MenuProduct() {
    }

    public MenuProduct(Long quantity, UUID productId, BigDecimal price) {
        this(null, quantity, productId, price);
    }

    public MenuProduct(Long seq, Long quantity, UUID productId, BigDecimal price) {
        this.seq = seq;
        this.quantity = new MenuQuantity(quantity);
        this.productId = productId;
        this.price = new MenuPrice(price);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public Long getQuantity() {
        return quantity.getQuantity();
    }

    public MenuPrice getTotalPrice() {
        return price.multiply(getQuantity());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProduct)) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
