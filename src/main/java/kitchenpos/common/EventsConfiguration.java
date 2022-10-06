package kitchenpos.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfiguration implements InitializingBean {

    private final ApplicationEventPublisher eventPublisher;

    public EventsConfiguration(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Events.setPublisher(eventPublisher);
    }
}
