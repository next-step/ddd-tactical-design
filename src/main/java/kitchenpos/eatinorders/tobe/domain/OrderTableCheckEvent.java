package kitchenpos.eatinorders.tobe.domain;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.DomainEvents;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.UUID;

public class OrderTableCheckEvent extends ApplicationEvent{

    private UUID orderTableId;

    public OrderTableCheckEvent(Object source, UUID orderTableId) {
        super(source);
        this.orderTableId = orderTableId;
    }

    public OrderTableCheckEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
