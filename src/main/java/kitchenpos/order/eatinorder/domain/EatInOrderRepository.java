package kitchenpos.order.eatinorder.domain;

public interface EatInOrderRepository {

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, EatInOrderStatus status);
}

