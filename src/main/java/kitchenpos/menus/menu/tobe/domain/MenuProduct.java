package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.tobe.domain.vo.Quantity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Menu menu;

    @Embedded
    private Price price;

    @Embedded
    private Quantity quantity;

    protected MenuProduct() {
    }

    private MenuProduct(final UUID productId, final Price price, final Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    protected static MenuProduct create(final UUID productId, final Price price, final Quantity quantity) {
        return new MenuProduct(productId, price, quantity);
    }

    protected void makeRelation(final Menu menu) {
        this.menu = menu;
    }

    protected void changePrice(final Price price) {
        this.price = price;
    }

    public Price amount() {
        return price.multiply(quantity.value());
    }

    public UUID productId() {
        return productId;
    }

    public Price price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
