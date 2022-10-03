package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.vo.Price;
import kitchenpos.menus.tobe.vo.Quantity;

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

    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuId;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Price price;

    protected MenuProduct() {

    }

    public MenuProduct(UUID menuId, UUID productId, Quantity quantity, Price price) {
        this.menuId = menuId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
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

    public UUID getMenuId() {
        return menuId;
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
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
