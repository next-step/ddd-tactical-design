package kitchenpos.eatinorders.tobe.domain.common;

import jakarta.persistence.*;
import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.MenuId;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    @Column(name = "menu_id", columnDefinition = "binary(16)")
    private MenuId menuId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Embedded
    private Price price;

    protected OrderLineItem() {
    }

    public OrderLineItem(Long seq, MenuId menuId, long quantity, Price price) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    public Price amount() {
        return price.multiply(quantity);
    }

    public boolean isSameMenuPrice(MenuId otherMenuId, Price otherPrice) {
        return otherMenuId.equals(menuId)
                && otherPrice.same(price);
    }

    public Long seq() {
        return seq;
    }

    public MenuId menuId() {
        return menuId;
    }

    public long quantity() {
        return quantity;
    }

    public Price price() {
        return price;
    }
}
