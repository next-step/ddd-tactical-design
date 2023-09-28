package kitchenpos.deliveryorders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeliveryOrderLineItemQuantity {
    @Column
    private long quantity;

    protected DeliveryOrderLineItemQuantity() {
    }

    public DeliveryOrderLineItemQuantity(long quantity) {
        validateDeliveryOrderLineItemQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateDeliveryOrderLineItemQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("배달 주문의 주문 항목 개수는 0이상이어야 합니다.");
        }
    }

    public long getQuantity() {
        return quantity;
    }
}
