package kitchenpos.eatinorders.todo.domain.ordertable;

public interface OrderClient {
    boolean containsInvalidOrderForClearOrderTable(OrderTable orderTable);
}
