package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.menus.tobe.domain.common.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Transient
    private UUID productId;

    @Embedded
    private Quantity quantity;

    @Transient
    private Price price;

    protected MenuProduct() {}

    public MenuProduct(Long seq, long quantity, UUID productId, BigDecimal productPrice) {
        this.seq = seq;
        this.quantity = new Quantity(quantity);
        this.productId = productId;
        this.price = new Price(productPrice).multiplyBy(quantity);
    }

    public Long getSeq() {
        return seq;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Price totalPrice() {
        return price;
    }
}
