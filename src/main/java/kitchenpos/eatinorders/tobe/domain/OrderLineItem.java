package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.domain.Menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private Menu menu;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID menuId;

    @Transient
    private BigDecimal price;

    protected OrderLineItem() {
    }

    public OrderLineItem(Menu menu, long quantity) {
        this.menu = menu;
        this.quantity = quantity;
        this.menuId = menu.id();
        this.price = menu.priceValue();
    }

    public Menu menu() {
        return menu;
    }

    public long quantity() {
        return quantity;
    }

    public BigDecimal price() {
        return price;
    }

    public void validateMenuIsDisplayed() {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItem that = (OrderLineItem) o;
        return quantity == that.quantity && Objects.equals(seq, that.seq) && Objects.equals(menu, that.menu) && Objects.equals(menuId, that.menuId) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, menu, quantity, menuId, price);
    }
}
