package kitchenpos.orders.common.domain.tobe;

import jakarta.persistence.*;
import kitchenpos.menus.domain.tobe.Menu;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private UUID menuId;

    private BigDecimal price;

    @Embedded
    private MenuQuantity quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(Menu menu, MenuQuantity quantity) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
        this.menuId = menu.getId();
        this.price = menu.getPrice();
        this.quantity = quantity;
    }
}
