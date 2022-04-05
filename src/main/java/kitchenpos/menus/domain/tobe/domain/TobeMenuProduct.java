package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.vo.MenuProductQuantity;
import kitchenpos.menus.domain.tobe.domain.vo.MenuProductSeq;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class TobeMenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private MenuProductSeq seq;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "product_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private TobeProduct product;

    @Column(name = "quantity", nullable = false)
    private MenuProductQuantity quantity;

    @Transient
    private ProductId productId;

    public TobeMenuProduct() {
    }

    public MenuProductSeq getSeq() {
        return seq;
    }

    public TobeProduct getProduct() {
        return product;
    }

    public MenuProductQuantity getQuantity() {
        return quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public BigDecimal calculateAmount() {
        return product.getPrice()
                .getValue()
                .multiply(BigDecimal.valueOf(quantity.getValue()));
    }
}
