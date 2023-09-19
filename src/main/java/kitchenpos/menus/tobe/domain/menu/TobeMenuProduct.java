package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class TobeMenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private TobeMenuProductQuantity quantity;

    @Column(name = "product_id")
    private UUID productId;

    @Transient
    private MenuPrice price;

    protected TobeMenuProduct() {
    }

    public TobeMenuProduct(UUID productId, MenuPrice menuPrice, TobeMenuProductQuantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = menuPrice.multiply(quantity.getQuantity());
    }

    public TobeMenuProduct(Long seq, UUID productId, MenuPrice menuPrice, TobeMenuProductQuantity quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.price = menuPrice.multiply(quantity.getQuantity());
    }

    public Long getSeq() {
        return seq;
    }

    public TobeMenuProductQuantity getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public MenuPrice getPrice() {
        return price;
    }
}
