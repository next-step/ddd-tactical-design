package kitchenpos.eatinorders.todo.domain.ordertables;

public interface OrderClient {
    boolean containsInvalidOrderForClearOrderTable(OrderTable orderTable);
}
