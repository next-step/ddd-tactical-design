package kitchenpos.apply.menus.tobe.domain;


import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "menu_product_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private MenuProductQuantity quantity;

    public MenuProduct(Long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = new MenuProductQuantity(quantity);
    }

    protected MenuProduct() { }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity.value();
    }

    public String getProductId() {
        return String.valueOf(productId);
    }
}
