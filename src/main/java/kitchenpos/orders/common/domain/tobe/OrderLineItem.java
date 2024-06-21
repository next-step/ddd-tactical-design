package kitchenpos.orders.common.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.Menu;

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
