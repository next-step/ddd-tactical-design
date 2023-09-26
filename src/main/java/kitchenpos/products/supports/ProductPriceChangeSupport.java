package kitchenpos.products.supports;


import kitchenpos.products.event.ProductPriceChangeEvent;
import org.springframework.context.event.EventListener;

public interface ProductPriceChangeSupport {

    @EventListener
    void changeProductPrice(final ProductPriceChangeEvent productPriceChangeEvent);
}
