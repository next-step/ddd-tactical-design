package kitchenpos.menu.adapter.in.eventlistener;

import kitchenpos.menu.application.port.in.UpdateMenuDisplayStatusUseCase;
import kitchenpos.shared.event.ProductPriceChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceChangeListener {

    private final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase;

    public ProductPriceChangeListener(final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase) {
        this.updateMenuDisplayStatusUseCase = updateMenuDisplayStatusUseCase;
    }

    @EventListener
    public void handleProductPriceChange(ProductPriceChangedEvent event) {
        updateMenuDisplayStatusUseCase.execute(event.getProductId());
    }
}

