package kitchenpos.menus.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    private static final String LESS_THAN_ZERO_MESSAGE = "수량은 0보다 작을 수 없습니다.";

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

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID productId;

    public MenuProduct(final long quantity, final Product product) {
        if (quantity < 0) {
            new IllegalArgumentException(LESS_THAN_ZERO_MESSAGE);
        }
        this.quantity = quantity;
        this.product = product;
    }

    public MenuProduct() {
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }

    public Price getSumOfPrice() {
        return product.getPrice().multiply(quantity);
    }

    public boolean lessThan(final Price price) {
        if (this.getSumOfPrice().compareTo(price) < 0) {
            return true;
        }
        return false;
    }
}
