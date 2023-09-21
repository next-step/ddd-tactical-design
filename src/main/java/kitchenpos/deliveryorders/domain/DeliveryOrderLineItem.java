package kitchenpos.deliveryorders.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "delivery_order_line_item")
@Entity
public class DeliveryOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;
    @Embedded
    private DeliveryOrderMenu deliveryOrderMenu;
    @Embedded
    private DeliveryOrderQuantity quantity;

    protected DeliveryOrderLineItem() {
    }

    public DeliveryOrderLineItem(DeliveryOrderMenu deliveryOrderMenu, DeliveryOrderQuantity quantity) {
        this.deliveryOrderMenu = deliveryOrderMenu;
        this.quantity = quantity;
    }

    public DeliveryOrderMenuPrice menuPrice() {
        return deliveryOrderMenu.menuPrice()
            .multiply(quantity.value());
    }
}
