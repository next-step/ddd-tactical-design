package kitchenpos.takeoutorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.sharedkernel.Order;
import kitchenpos.sharedkernel.OrderStatus;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "take_out_order")
@Entity
public class TakeOutOrder extends Order {

    @Embedded
    private OrderLineItems orderLineItems;

    protected TakeOutOrder() {

    }

    public TakeOutOrder(
        final UUID id,
        final OrderStatus status,
        final LocalDateTime orderDateTime,
        final OrderLineItems orderLineItems
    ) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
    }

    public static TakeOutOrder create(
        final List<Menu> menus,
        final List<TakeOutOrderLineItem> takeOutOrderLineItemList
    ) {
        return new TakeOutOrder(
            UUID.randomUUID(),
            OrderStatus.WAITING,
            LocalDateTime.now(),
            new OrderLineItems(takeOutOrderLineItemList, createMenuMap(menus))
        );
    }
}
