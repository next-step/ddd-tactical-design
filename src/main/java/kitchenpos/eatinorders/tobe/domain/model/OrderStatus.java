package kitchenpos.eatinorders.tobe.domain.model;

public interface OrderStatus {

    OrderStatus proceed() throws IllegalStateException;

    String getStatus();
}
