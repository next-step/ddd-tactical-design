package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    private Menu menu;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Price price;

    protected MenuProduct() {

    }

    public MenuProduct(UUID productId, Quantity quantity, Price price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public void makeRelation(Menu menu) {
        this.menu = menu;
    }

    public BigDecimal totalPrice() {
        return price.getValue().multiply(quantity.toBigDecimal());
    }

    public void changePrice(Price price) {
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }

    public Menu getMenuId() {
        return menu;
    }

    public UUID getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProduct that = (MenuProduct) o;

        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;
        if (menu != null ? !menu.equals(that.menu) : that.menu != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (menu != null ? menu.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
