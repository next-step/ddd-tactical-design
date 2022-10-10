package kitchenpos.common;

import org.springframework.context.ApplicationEventPublisher;

public class Events {

    static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        if (Events.publisher == null) {
            Events.publisher = publisher;
        }
    }

    public static void raise(Object event) {
        if (Events.publisher != null) {
            publisher.publishEvent(event);
        }
    }

    private Events() {
    }
}
