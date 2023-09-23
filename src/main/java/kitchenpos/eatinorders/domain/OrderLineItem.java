package kitchenpos.eatinorders.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "menu_id")
    private UUID menuId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "price", nullable = false)
    private Price price;

    protected OrderLineItem() {
    }

    public OrderLineItem(UUID menuId, long quantity, Long price) {
        if (menuId == null) {
            throw new IllegalArgumentException("메뉴의 id는 null일 수 없습니다.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException(String.format("수량은 0개 이상이어야 합니다. 현재 값: %s", quantity));
        }
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = new Price(price);
    }
}
