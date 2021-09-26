package kitchenpos.common.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class FakeEventPublisher implements ApplicationEventPublisher {
    @Override
    public void publishEvent(ApplicationEvent event) {
        ApplicationEventPublisher.super.publishEvent(event);
    }

    @Override
    public void publishEvent(Object event) {

    }
}
