package kitchenpos.eatinorders.tobe.domain;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import kitchenpos.menus.tobe.domain.Menu;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    public static final String INVALID_QUANTITY_ERROR = "수량은 0 이상이어야 합니다.";
    public static final String MENU_NOT_DISPLAYED_ERROR = "메뉴가 표시되지 않습니다.";
    public static final String MENU_PRICE_MISMATCH_ERROR = "메뉴 가격이 일치하지 않습니다.";

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

    public OrderLineItem(OrderType orderType, BigDecimal price, long quantity, Menu menu) {
        this(orderType, new Random().nextLong(), price, quantity, menu);
    }

    public OrderLineItem(OrderType orderType, Long seq, BigDecimal price, long quantity, Menu menu) {
        if (orderType != OrderType.EAT_IN && quantity < 0) {
            throw new IllegalArgumentException(INVALID_QUANTITY_ERROR);
        }

        if (!menu.isDisplayed()) {
            throw new IllegalStateException(MENU_NOT_DISPLAYED_ERROR);
        }

        if (menu.getPrice().compareTo(price) != 0) {
            throw new IllegalArgumentException(MENU_PRICE_MISMATCH_ERROR);
        }

        this.seq = seq;
        this.menuId = menu.getId();
        this.price = price;
        this.quantity = quantity;
        this.menu = menu;
    }

    public Long getSeq() {
        return seq;
    }

    public Menu getMenu() {
        return menu;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

