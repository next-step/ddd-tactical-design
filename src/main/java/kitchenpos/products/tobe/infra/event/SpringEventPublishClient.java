package kitchenpos.products.tobe.infra.event;

import kitchenpos.products.tobe.domain.interfaces.event.EventPublishClient;
import kitchenpos.products.tobe.ui.dto.ProductChangePriceEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublishClient implements EventPublishClient {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventPublishClient(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(ProductChangePriceEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
