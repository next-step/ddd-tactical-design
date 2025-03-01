package kitchenpos.shared;

import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayDeque;
import java.util.Queue;

public class InMemoryApplicationEventPublisher implements ApplicationEventPublisher {

    private final Queue<Object> events;

    public InMemoryApplicationEventPublisher() {
        this(new ArrayDeque<>());
    }

    public InMemoryApplicationEventPublisher(final Queue<Object> events) {
        this.events = events;
    }

    @Override
    public void publishEvent(final Object event) {
        events.add(event);
    }
}
