package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "tobe_menu_product")
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

    @Embedded
    private Quantity quantity;

    public MenuProduct() {

    }

    public MenuProduct(Product product, Quantity quantity) {
        this.quantity = quantity;
        this.product = product;
    }

    public BigDecimal sum() {
        return this.product.price().multiply(this.quantity.quantity());
    }
}
