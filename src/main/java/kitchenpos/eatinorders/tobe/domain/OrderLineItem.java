package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;

@Table(name = "tb_order_line_item")
@Entity(name = "tb_order_line_item")
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Quantity quantity;

    @Embedded
    private DisplayedMenu menu;

    protected OrderLineItem() {
    }

    public OrderLineItem(Quantity quantity, DisplayedMenu menu) {
        this.quantity = quantity;
        this.menu = menu;
    }

    public UUID getMenuId() {
        return menu.getId();
    }

    public void withMenu(DisplayedMenu menu) {
        this.menu = menu;
    }

    public Price getMenuPrice() {
        return menu.getPrice();
    }

    public boolean samePrice(Price price) {
        return getMenuPrice().isSame(price);
    }

    public DisplayedMenu getMenu() {
        return menu;
    }
}
