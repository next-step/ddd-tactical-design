package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.ToBeMenu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class ToBeOrderLineItem {
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
    private ToBeMenu menu;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID menuId;

    @Transient
    private BigDecimal price;

    public ToBeOrderLineItem() {
    }

    public ToBeOrderLineItem(ToBeMenu menu, long quantity){
        this.menu = menu;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public ToBeMenu getMenu() {
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
