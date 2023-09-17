package kitchenpos.menus.tobe.domain.menuproduct;

import kitchenpos.products.tobe.domain.TobeProduct;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class TobeMenuProduct {
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
    private TobeProduct product;

    private MenuProductQuantity quantity;

    @Transient
    private UUID productId;

    protected TobeMenuProduct() {
    }

    public TobeMenuProduct(TobeProduct product, MenuProductQuantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public TobeMenuProduct(Long seq, TobeProduct product, MenuProductQuantity quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
    }

    public TobeMenuProduct(MenuProductQuantity quantity, UUID productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public Long getSeq() {
        return seq;
    }

    public TobeProduct getProduct() {
        return product;
    }

    public MenuProductQuantity getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
