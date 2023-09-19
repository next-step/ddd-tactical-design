package kitchenpos.eatinorders.tobe.domain;


public interface EventPublisher {

    void publish(BaseEvent event);
}
