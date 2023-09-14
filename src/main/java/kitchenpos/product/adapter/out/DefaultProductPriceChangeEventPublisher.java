package kitchenpos.product.adapter.out;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.menu.adapter.in.MenuDisplayingRearranger;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.support.constant.Name;

public class DefaultProductPriceChangeEventPublisher implements ProductPriceChangeEventPublisher {

    private final MenuDisplayingRearranger rearranger;

    public DefaultProductPriceChangeEventPublisher(final MenuDisplayingRearranger rearranger) {
        this.rearranger = rearranger;
    }

    @Override
    public void publish(final UUID id) {
        checkNotNull(id, Name.ID);

        rearranger.execute(id);
    }
}
