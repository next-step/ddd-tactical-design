package kitchenpos.menus.tobe.domain;


import kitchenpos.products.tobe.domain.ToBeProduct;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class ToBeMenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private ToBeProduct product;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private MenuProductQuantity quantity;

    @Transient
    private UUID productId;

    public ToBeMenuProduct() {
    }

    public ToBeMenuProduct(Long seq, ToBeProduct product, long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = new MenuProductQuantity(quantity);
    }

    public ToBeMenuProduct(ToBeProduct product, long quantity) {
        this.product = product;
        this.quantity = new MenuProductQuantity(quantity);
    }

    public ToBeMenuProduct(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = new MenuProductQuantity(quantity);
    }
    public Long getSeq() {
        return seq;
    }

    public ToBeProduct getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity.getQuantity();
    }

    public UUID getProductId() {
        return productId;
    }
}
