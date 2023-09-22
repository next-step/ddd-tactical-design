package kitchenpos.product.adapter.out;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.support.constant.Name;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultProductPriceChangeEventPublisher implements ProductPriceChangeEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    public DefaultProductPriceChangeEventPublisher(
        final ApplicationEventPublisher applicationEventPublisher) {

        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(final UUID id) {
        checkNotNull(id, Name.ID);

        applicationEventPublisher.publishEvent(new ProductPriceChangeEvent(id));
    }
}
