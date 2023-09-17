package kitchenpos.menus.tobe.domain;


import kitchenpos.products.domain.Product;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class ToBeMenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID productId;


    public ToBeMenuProduct(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    public ToBeMenuProduct(Long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }


    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Long getSeq() {
        return seq;
    }


    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
