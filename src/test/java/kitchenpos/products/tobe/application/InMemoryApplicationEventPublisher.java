package kitchenpos.products.tobe.application;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class InMemoryApplicationEventPublisher implements ApplicationEventPublisher {
    private final Queue<Object> publishers = new LinkedList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        ApplicationEventPublisher.super.publishEvent(event);
    }

    @Override
    public void publishEvent(Object event) {
        publishers.add(event);
    }

    public Object poll() {
        return publishers.poll();
    }
}
