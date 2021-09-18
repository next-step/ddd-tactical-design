package kitchenpos.eatinorders.tobe.domain.model;

public interface OrderStatus {

    OrderStatus proceed(final Order order) throws IllegalStateException;

    String getStatus();
}
