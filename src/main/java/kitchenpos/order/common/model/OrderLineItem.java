package kitchenpos.order.common.model;

import jakarta.persistence.*;
import kitchenpos.menu.domain.model.Menu;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.order.common.exception.OrderLineItemExceptionMessage.*;

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

    public OrderLineItem() {
    }

    public OrderLineItem(Menu menu, long quantity, UUID menuId, BigDecimal price) {
        validateQuantity(quantity);
        validateMenuDisplay(menu);
        validatePrice(menu, price);
        this.menu = menu;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    private void validateQuantity(long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(ORDER_LINE_ITEM_QUANTITY_EXCEPTION.getMessage());
        }
    }

    private void validateMenuDisplay(Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException(ORDER_LINE_ITEM_MENU_DISPLAY_EXCEPTION.getMessage());
        }
    }

    private void validatePrice(Menu menu, BigDecimal price) {
        if (menu.getInnerPrice().compareTo(price) != 0) {
            throw new IllegalArgumentException(ORDER_LINE_ITEM_PRICE_EXCEPTION.getMessage());
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public void addMenu(final Menu menu) {
        this.menu = menu;
    }

    public long getQuantity() {
        return quantity;
    }

    public void addQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
