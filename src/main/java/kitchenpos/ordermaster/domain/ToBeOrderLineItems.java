package kitchenpos.ordermaster.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class ToBeOrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "to_be_order_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_to_be_order_line_item_to_orders")
    )
    private List<ToBeOrderLineItem> orderLineItems;

    protected ToBeOrderLineItems() {
    }

    public ToBeOrderLineItems(List<ToBeOrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 내역이 없으면 등록할 수 없다.");
        }
        this.orderLineItems = orderLineItems;
    }
}
