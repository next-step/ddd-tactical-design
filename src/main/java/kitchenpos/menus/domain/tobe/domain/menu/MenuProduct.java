package kitchenpos.menus.domain.tobe.domain.menu;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import kitchenpos.common.domain.ProductId;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @EmbeddedId
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private MenuProductSeq seq;

    @Column(name = "product_id", nullable = false, columnDefinition = "varbinary(16)")
    private ProductId productId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProduct() {
    }

    public MenuProduct(final MenuProductSeq seq, final ProductId productId, final long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
