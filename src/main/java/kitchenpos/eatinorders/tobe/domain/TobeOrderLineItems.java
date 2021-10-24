package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Embeddable
public class TobeOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<TobeOrderLineItem> orderLineItems;

    protected TobeOrderLineItems() {
    }

    public TobeOrderLineItems(List<TobeOrderLineItem> orderLineItems) {
        validationOrderLineItems(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

    private void validationOrderLineItems(List<TobeOrderLineItem> orderLineItems) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문 상품이 없습니다.");
        }
    }

    public List<TobeOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public BigDecimal itemsTotalPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (TobeOrderLineItem item: orderLineItems) {
            sum = sum.add(item.getPrice());
        }
        return sum;
    }
}
