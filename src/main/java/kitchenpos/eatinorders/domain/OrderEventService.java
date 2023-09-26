package kitchenpos.eatinorders.domain;

public interface OrderEventService {
    void notifyOrderComplete(OrderCompleteEvent event);
}
