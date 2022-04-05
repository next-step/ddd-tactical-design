package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.common.exception.NamingRuleViolationException;
import kitchenpos.common.exception.PricingRuleViolationException;
import kitchenpos.common.policy.NamingRule;
import kitchenpos.common.policy.PricingRule;
import kitchenpos.menus.domain.tobe.domain.vo.*;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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

    public TobeMenuProduct(TobeProduct product, MenuProductQuantity quantity, ProductId productId) {
        this.product = product;
        this.quantity = quantity;
        this.productId = productId;
    }

    public static class Builder {
        private long menuProductSeq;
        private TobeProduct product;
        private long quantity;
        private ProductId productId;

        public Builder() {

        }

        public Builder name(final long menuProductSeq) {
            this.menuProductSeq = menuProductSeq;
            return this;
        }

        public Builder product(final TobeProduct product) {
            this.product = product;
            return this;
        }

        public Builder quantity(final long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder productId(final ProductId productId) {
            this.productId = productId;
            return this;
        }

        public TobeMenuProduct build() {
            if (Objects.isNull(product) || Objects.isNull(quantity) || Objects.isNull(productId) ) {
                throw new IllegalArgumentException();
            }
            return new TobeMenuProduct(product, new MenuProductQuantity(quantity), productId);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TobeMenuProduct that = (TobeMenuProduct) o;

        return seq != null ? seq.equals(that.seq) : that.seq == null;
    }

    @Override
    public int hashCode() {
        return seq != null ? seq.hashCode() : 0;
    }
}
