package kitchenpos.eatinorders.domain.orders;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected EatInOrderLineItem() {
    }

    private EatInOrderLineItem(long quantity, UUID menuId, BigDecimal price) {
        this(null, quantity, menuId, price);
    }

    private EatInOrderLineItem(Long seq, long quantity, UUID menuId, BigDecimal price) {
        this.seq = seq;
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public static EatInOrderLineItem from(final EatInOrderLineItemMaterial orderLineItemMaterial, final OrderedMenus orderedMenus, final MenuClient menuClient) {
        if (menuClient.isHide(orderLineItemMaterial.getMenuId())) {
            throw new IllegalStateException("비노출된 메뉴는 주문할 수 없습니다. menuId = " + orderLineItemMaterial.getMenuId());
        }
        if (!orderedMenus.containsMenuId(orderLineItemMaterial.getMenuId())) {
            throw new IllegalStateException("주문할 수 없는 메뉴입니다. 존재하지 않는 메뉴입니다. menuId = " + orderLineItemMaterial.getMenuId());
        }
        return new EatInOrderLineItem(
                orderLineItemMaterial.getQuantity(),
                orderLineItemMaterial.getMenuId(),
                orderedMenus.getOrderedMenuByMenuId(orderLineItemMaterial.getMenuId()).getPrice()
        );
    }

    public Long getSeq() {
        return seq;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItem that = (EatInOrderLineItem) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
