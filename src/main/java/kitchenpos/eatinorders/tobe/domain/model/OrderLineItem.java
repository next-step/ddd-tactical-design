package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.common.domain.model.Price;
import kitchenpos.common.domain.model.Quantity;
import kitchenpos.eatinorders.tobe.domain.validator.OrderLineItemValidator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private Price price;

    private Quantity quantity;

    private UUID menuId;

    @ManyToOne
    private Order order;

    protected OrderLineItem() {

    }

    public OrderLineItem(Price price, Quantity quantity, UUID menuId, OrderLineItemValidator orderLineItemValidator) {
        this.price = price;
        this.quantity = quantity;
        this.menuId = menuId;
        orderLineItemValidator.validate(this);
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

    public Quantity getQuantity() {
        return quantity;
    }
}
