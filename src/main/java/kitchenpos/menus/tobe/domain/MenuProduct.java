package kitchenpos.menus.tobe.domain;

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
import java.math.BigDecimal;
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

    protected MenuProduct() {
    }

    public Price getSumOfPrice() {
        return new Price(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
    }

    public boolean lessThan(final Price price) {
        if (this.getSumOfPrice().compareTo(price) < 0) {
            return true;
        }
        return false;
    }
}
