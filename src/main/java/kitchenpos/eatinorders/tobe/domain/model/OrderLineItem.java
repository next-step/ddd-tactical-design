package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.common.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.validator.OrderLineItemValidator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private Price price;

    private OrderLineItemQuantity orderLineItemQuantity;

    private UUID menuId;

    protected OrderLineItem() {

    }

    public OrderLineItem(Price price, OrderLineItemQuantity orderLineItemQuantity, UUID menuId, OrderLineItemValidator orderLineItemValidator) {
        this.price = price;
        this.orderLineItemQuantity = orderLineItemQuantity;
        this.menuId = menuId;
        orderLineItemValidator.compareToMenuPrice(this);
    }

    public UUID getMenuId() {
        return this.menuId;
    }

    public Price getPrice() {
        return this.price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderLineItemQuantity getOrderLineItemQuantity() {
        return this.orderLineItemQuantity;
    }
}
