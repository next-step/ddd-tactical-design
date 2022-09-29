package kitchenpos;

import org.springframework.context.ApplicationEventPublisher;

public class FakeEventPublisher implements ApplicationEventPublisher {

    @Override
    public void publishEvent(Object event) {
    }
}
