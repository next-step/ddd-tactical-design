package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;

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
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private TobeProduct product;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected TobeMenuProduct() {
    }

    public TobeMenuProduct(TobeProduct product, long quantity) {
        validationQuantity(quantity);
        this.product = product;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public TobeProduct getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal menuProductPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    private void validationQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }
}
