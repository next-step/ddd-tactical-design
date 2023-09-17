package kitchenpos.menus.domain;


import kitchenpos.common.values.Quantity;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Quantity quantity;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, Quantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProduct(Long seq, UUID productId, Quantity quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }


    public Long getSeq() {
        return seq;
    }


    public Quantity getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
