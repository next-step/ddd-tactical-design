package kitchenpos.ordermaster.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "to_be_order_line_item")
@Entity
public class ToBeOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;
    @Embedded
    private OrderMenu orderMenu;
    @Embedded
    private OrderQuantity quantity;

    protected ToBeOrderLineItem() {
    }

    public ToBeOrderLineItem(OrderMenu orderMenu, OrderQuantity quantity) {
        this.orderMenu = orderMenu;
        this.quantity = quantity;
    }
}
