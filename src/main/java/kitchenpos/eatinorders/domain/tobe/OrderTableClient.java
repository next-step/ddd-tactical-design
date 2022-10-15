package kitchenpos.eatinorders.domain.tobe;

public interface OrderTableClient {
    void clear(OrderTableId orderTableId);

    boolean isEmptyOrderTable(OrderTableId orderTableId);
}
