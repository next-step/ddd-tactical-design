package kitchenpos.deliveryorders.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderMasterId {
    @Column(name = "orders_master", columnDefinition = "binary(16)")
    private UUID orderMasterId;
    @Column(name = "total_order_price")
    private BigDecimal totalOrderPrice;

    protected OrderMasterId() {
    }

    public OrderMasterId(UUID orderMasterId, BigDecimal totalOrderPrice) {
        this.orderMasterId = orderMasterId;
        this.totalOrderPrice = totalOrderPrice;
    }

    public BigDecimal getTotalOrderPrice() {
        return totalOrderPrice;
    }
}
