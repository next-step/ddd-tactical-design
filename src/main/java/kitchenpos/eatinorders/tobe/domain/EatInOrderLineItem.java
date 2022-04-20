package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.Quantity;
import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {
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
    private Menu menu;

    @Embedded
    private Quantity quantity;

    @Transient
    @Embedded
    private Price price;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(final Menu menu, final Quantity quantity) {
        this.menu = menu;
        this.quantity = quantity;
        this.price = menu.getPrice();
    }

    public Menu getMenu() {
        return menu;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public UUID getMenuId() {
        return menu.getId();
    }

    public Price getPrice() {
        return price;
    }
}
