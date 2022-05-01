package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderLineItem;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderId;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import java.time.LocalDateTime;
import java.util.List;

public class EatInOrderResponse {
    private OrderId id;
    private OrderStatus status;
    private LocalDateTime orderDateTime;
    private List<TobeOrderLineItem> orderLineItems;
    private TobeOrderTable table;
    private OrderTableId tableId;

    public EatInOrderResponse(OrderId id, OrderStatus status, LocalDateTime orderDateTime, List<TobeOrderLineItem> orderLineItems, TobeOrderTable table, OrderTableId tableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.table = table;
        this.tableId = tableId;
    }

    public EatInOrderResponse(EatInOrder order) {
        this.id = order.getId();
        this.status = order.getStatus();
        this.orderDateTime = order.getOrderDateTime();
        this.orderLineItems = order.getOrderLineItems().getOrderLineItems();
        this.table = order.getOrderTable();
        this.tableId = order.getOrderTableId();
    }

    public EatInOrderResponse() {
    }

    public OrderId getId() {
        return id;
    }

    public void setId(OrderId id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<TobeOrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<TobeOrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public TobeOrderTable getTable() {
        return table;
    }

    public void setTable(TobeOrderTable table) {
        this.table = table;
    }

    public OrderTableId getTableId() {
        return tableId;
    }

    public void setTableId(OrderTableId tableId) {
        this.tableId = tableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EatInOrderResponse that = (EatInOrderResponse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (status != that.status) return false;
        if (orderDateTime != null ? !orderDateTime.equals(that.orderDateTime) : that.orderDateTime != null)
            return false;
        if (orderLineItems != null ? !orderLineItems.equals(that.orderLineItems) : that.orderLineItems != null)
            return false;
        if (table != null ? !table.equals(that.table) : that.table != null) return false;
        return tableId != null ? tableId.equals(that.tableId) : that.tableId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (orderDateTime != null ? orderDateTime.hashCode() : 0);
        result = 31 * result + (orderLineItems != null ? orderLineItems.hashCode() : 0);
        result = 31 * result + (table != null ? table.hashCode() : 0);
        result = 31 * result + (tableId != null ? tableId.hashCode() : 0);
        return result;
    }
}
