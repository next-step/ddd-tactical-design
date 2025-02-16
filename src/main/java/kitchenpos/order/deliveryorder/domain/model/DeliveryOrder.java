package kitchenpos.order.deliveryorder.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

//@Entity
public class DeliveryOrder {

//    @Id
//    @GeneratedValue
    private UUID id;

//    @Enumerated
    private DeliveryOrderFlow orderFlow;

//    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinColumn(
//            name = "order_id",
//            nullable = false,
//            columnDefinition = "binary(16)",
//            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
//    )
//    private List<OrderLineItem> orderLineItems;

//    @Column(name = "delivery_address")
    private String deliveryAddress;

    public boolean validateOrderFlowAndFindNextStep(DeliveryOrderStatus orderStatus) {
        if (orderFlow.validateOrderStatus(orderStatus)) {
            throw new IllegalArgumentException();
        }
        return orderFlow.isRiderNecessary(orderStatus);
    }

    public UUID getId() {
        return id;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
