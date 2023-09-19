package kitchenpos.menus.tobe.domain.menu;

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

    private TobeMenuProductQuantity quantity;

    @Transient
    private UUID productId;

    @Transient
    private TobeMenuProductPrice price;

    protected TobeMenuProduct() {
    }

    public TobeMenuProduct(TobeProduct product, TobeMenuProductQuantity quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = TobeMenuProductPrice.multiply(product.getBigDecimalPrice(), quantity.getQuantity());
    }

    public TobeMenuProduct(Long seq, TobeProduct product, TobeMenuProductQuantity quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
        this.price = TobeMenuProductPrice.multiply(product.getBigDecimalPrice(), quantity.getQuantity());
    }

    public Long getSeq() {
        return seq;
    }

    public TobeProduct getProduct() {
        return product;
    }

    public TobeMenuProductQuantity getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public TobeMenuProductPrice getPrice() {
        return price;
    }
}
