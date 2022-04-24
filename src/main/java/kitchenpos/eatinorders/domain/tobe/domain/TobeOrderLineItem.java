package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.menus.domain.tobe.domain.vo.MenuPrice;
import kitchenpos.support.vo.Quantity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class TobeOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private TobeMenu menu;

    @Embedded
    private Quantity quantity;

    @Transient
    private MenuId menuId;

    @Transient
    private MenuPrice price;

    public TobeOrderLineItem() {
    }

    public TobeOrderLineItem(TobeMenu menu, MenuPrice menuPrice, Quantity quantity) {
        this.menu = menu;
        this.quantity = quantity;
        this.menuId = menu.getId();
        this.price = menuPrice;
    }

    public Long getSeq() {
        return seq;
    }

    public TobeMenu getMenu() {
        return menu;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public MenuPrice getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TobeOrderLineItem that = (TobeOrderLineItem) o;

        return seq != null ? seq.equals(that.seq) : that.seq == null;
    }

    @Override
    public int hashCode() {
        return seq != null ? seq.hashCode() : 0;
    }
}
