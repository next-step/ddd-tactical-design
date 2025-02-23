package kitchenpos.menu.adapter.in.eventlistener;

import kitchenpos.menu.application.port.in.UpdateMenuProductPriceUseCase;
import kitchenpos.shared.event.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceChangeListener {
    private final UpdateMenuProductPriceUseCase updateMenuProductPriceUseCase;

    public ProductPriceChangeListener(
            final UpdateMenuProductPriceUseCase updateMenuProductPriceUseCase
    ) {
        this.updateMenuProductPriceUseCase = updateMenuProductPriceUseCase;
    }

    @EventListener
    public void handleProductPriceChange(ProductPriceChangedEvent event) {
        updateMenuProductPriceUseCase.execute(event.productId(), event.newPrice());
    }
}

