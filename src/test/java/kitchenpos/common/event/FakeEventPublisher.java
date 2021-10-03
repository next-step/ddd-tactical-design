package kitchenpos.common.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class FakeEventPublisher implements ApplicationEventPublisher {

    private int callCounter = 0;
    private Object event;

    @Override
    public void publishEvent(ApplicationEvent event) {
        ApplicationEventPublisher.super.publishEvent(event);
    }

    @Override
    public void publishEvent(Object event) {
        callCounter++;
        this.event = event;
    }

    public int getCallCounter() {
        return callCounter;
    }

    public Object getEvent() {
        return event;
    }
}
