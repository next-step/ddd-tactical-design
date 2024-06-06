package kitchenpos.menus.domain.tobe;

import kitchenpos.products.domain.tobe.ProductPriceChangedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MenuEventListener implements ApplicationListener<ProductPriceChangedEvent> {

    private final MenuRepository menuRepository;

    public MenuEventListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void onApplicationEvent(ProductPriceChangedEvent event) {
        List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());

        menus.stream()
                .forEach(a -> {
                    a.changeProductPrice(event.getProductId(), event.getPrice());
                });
    }
}
