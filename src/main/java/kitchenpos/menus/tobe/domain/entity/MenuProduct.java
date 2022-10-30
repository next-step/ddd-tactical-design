package kitchenpos.menus.tobe.domain.entity;

import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    @AttributeOverride(name = "quantity", column = @Column(name = "quantity", nullable = false))
    private MenuProductQuantity quantity;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private IncludedProduct includedProduct;

    public MenuProduct(MenuProductQuantity quantity, IncludedProduct includedProduct) {
        this.quantity = quantity;
        this.includedProduct = includedProduct;
    }

    public Price totalPrice() {
        return new Price(includedProduct.getPrice().getPrice().multiply(BigDecimal.valueOf(quantity.getQuantity())));
    }

    public IncludedProduct getProduct() {
        return includedProduct;
    }

    protected MenuProduct() {}
}
