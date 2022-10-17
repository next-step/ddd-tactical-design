package kitchenpos.eatinorders.feedback.application;

public interface OrderEventPublisher {
    void complete(Long orderTableId);
}
