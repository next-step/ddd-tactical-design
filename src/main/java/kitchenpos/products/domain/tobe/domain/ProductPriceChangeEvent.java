package kitchenpos.products.domain.tobe.domain;

import org.springframework.context.ApplicationEvent;

public class ProductPriceChangeEvent extends ApplicationEvent {
    public ProductPriceChangeEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
}
