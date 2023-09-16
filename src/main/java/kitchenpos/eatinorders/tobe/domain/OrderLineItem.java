package kitchenpos.eatinorders.tobe.domain;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private UUID menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private OrderLineItemQuantity quantity = OrderLineItemQuantity.INIT;

    public OrderLineItem(UUID menuId, OrderLineItemQuantity quantity) {
        if (isEmpty(menuId)) {
            throw new IllegalArgumentException("주문내역엔 메뉴가 있어야 합니다");
        }

        if (isEmpty(quantity)) {
            throw new IllegalArgumentException("주문내역엔 수량이 있어야 합니다");
        }

        this.menuId = menuId;
        this.quantity = quantity;
    }

    protected OrderLineItem() {
    }

    public Long getSeq() {
        return seq;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Order getOrder() {
        return order;
    }

    public OrderLineItemQuantity getQuantity() {
        return quantity;
    }
}
