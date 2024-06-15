package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.menus.tobe.domain.MenuPrice;

import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "menu_id", nullable = false, columnDefinition = "binary(16)")
    private UUID menuId;

    @Column(name = "price", nullable = false)
    private MenuPrice menuPrice;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected EatInOrderLineItem() {
    }

    private EatInOrderLineItem(UUID menuId, MenuPrice menuPrice, long quantity) {
        this.menuId = menuId;
        this.menuPrice = menuPrice;
        this.quantity = quantity;
    }

    public static EatInOrderLineItem create(UUID menuId, MenuPrice menuPrice, long quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("주문 수량은 0보다 커야 합니다.");
        }
        return new EatInOrderLineItem(menuId, menuPrice, quantity);
    }
}
