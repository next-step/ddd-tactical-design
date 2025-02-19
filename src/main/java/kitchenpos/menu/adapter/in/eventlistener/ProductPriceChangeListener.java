package kitchenpos.menu.adapter.in.eventlistener;

import kitchenpos.menu.application.port.in.UpdateMenuDisplayStatusUseCase;
import kitchenpos.menu.application.port.in.UpdateMenuProductPriceUseCase;
import kitchenpos.shared.event.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceChangeListener {

    private final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase;
    private final UpdateMenuProductPriceUseCase updateMenuProductPriceUseCase;

    public ProductPriceChangeListener(
            final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase,
            final UpdateMenuProductPriceUseCase updateMenuProductPriceUseCase
    ) {
        this.updateMenuDisplayStatusUseCase = updateMenuDisplayStatusUseCase;
        this.updateMenuProductPriceUseCase = updateMenuProductPriceUseCase;
    }

    @EventListener
    public void handleProductPriceChange(ProductPriceChangedEvent event) {
        updateMenuProductPriceUseCase.execute(event.productId(), event.newPrice());
        updateMenuDisplayStatusUseCase.execute(event.productId());
    }
}

