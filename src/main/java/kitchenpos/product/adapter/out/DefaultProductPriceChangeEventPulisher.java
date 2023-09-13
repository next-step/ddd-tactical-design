package kitchenpos.product.adapter.out;

import java.util.UUID;

import kitchenpos.menu.adapter.in.MenuDisplayingRearranger;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;

public class DefaultProductPriceChangeEventPulisher implements ProductPriceChangeEventPublisher {

	private final MenuDisplayingRearranger rearranger;

	public DefaultProductPriceChangeEventPulisher(final MenuDisplayingRearranger rearranger) {
		this.rearranger = rearranger;
	}

	@Override
	public void publish(final UUID id) {
		rearranger.rearrange(id);
	}
}
