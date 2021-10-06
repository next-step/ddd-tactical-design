package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.menus.tobe.menu.domain.MenuId;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "menu_id"))
    )
    private MenuId menuId;

    @Embedded
    private Quantity quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(final MenuId menuId, final Quantity quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId.getId();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderLineItem that = (OrderLineItem) o;
        return Objects.equals(seq, that.seq) || (Objects.equals(menuId, that.menuId) && Objects.equals(quantity, that.quantity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, quantity);
    }
}
