package kitchenpos.product.adapter.out;

import java.util.UUID;
import kitchenpos.menu.adapter.in.MenuDisplayingRearranger;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;

public class DefaultProductPriceChangeEventPublisher implements ProductPriceChangeEventPublisher {

    private final MenuDisplayingRearranger rearranger;

    public DefaultProductPriceChangeEventPublisher(final MenuDisplayingRearranger rearranger) {
        this.rearranger = rearranger;
    }

    @Override
    public void publish(final UUID id) {
        rearranger.rearrange(id);
    }
}
