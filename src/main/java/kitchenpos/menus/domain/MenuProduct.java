package kitchenpos.menus.domain;


import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    public MenuProduct(Long seq, UUID productId, long quantity) {
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
