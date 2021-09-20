package kitchenpos.eatinorders.tobe.domain.model;

public interface OrderStatus {

    OrderStatus proceed() throws IllegalStateException;

    String getStatus();

    boolean equals(final Object o);

    int hashCode();
}
