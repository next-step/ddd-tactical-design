package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
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

    private Product product;

    protected MenuProduct() {

    }

    @Embedded
    private MenuProductQuantity quantity;

    public MenuProduct(Product product, MenuProductQuantity quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }


    public Product getProduct() {
        return product;
    }


    public long getQuantityValue() {
        return quantity.getValue();
    }

    public UUID getProductId() {
        return product.getId();
    }

    public ProductPrice getProductPrice() {
        return product.getPrice().multiply(quantity.getValue());
    }
}
