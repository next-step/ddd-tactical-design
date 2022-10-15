package kitchenpos.products.infra;

import kitchenpos.global.event.ChangeProductPriceEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ChangeProductPriceEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public ChangeProductPriceEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void apply(final ChangeProductPriceEvent productPriceEvent) {
        applicationEventPublisher.publishEvent(productPriceEvent);
    }

}
