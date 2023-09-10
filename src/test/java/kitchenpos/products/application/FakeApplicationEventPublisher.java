package kitchenpos.products.application;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeApplicationEventPublisher implements ApplicationEventPublisher {
    private static final List<Object> events = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        events.add(event);
    }

    @Override
    public void publishEvent(Object event) {
        events.add(event);
    }

    public Object getFirst() {
        return events.stream().findFirst();
    }
    public Object pop() {
        Object result = events.stream().findFirst().get();
        events.remove(result);
        return result;
    }

}
