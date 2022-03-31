package kitchenpos.products.tobe.infra;

import kitchenpos.common.domain.ProductChangedEvent;
import kitchenpos.products.tobe.domain.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductProducerImpl implements ProductProducer {
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ProductProducerImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(final UUID productId) {
        eventPublisher.publishEvent(new ProductChangedEvent(productId));
    }
}
