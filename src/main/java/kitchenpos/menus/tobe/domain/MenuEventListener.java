package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductPriceChangedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MenuEventListener implements ApplicationListener<ProductPriceChangedEvent> {

    private final MenuRepository menuRepository;

    public MenuEventListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void onApplicationEvent(ProductPriceChangedEvent event) {
        UUID productId = event.getProductId();
        ProductPrice newPrice = event.getProductPrice();

        List<Menu> menus = menuRepository.findAllByProductId(productId);

        for (Menu menu : menus) {
            menu.getMenuProducts().changePriceById(productId, newPrice.getValue());

            if (menu.getPrice() > menu.getMenuProducts().totalAmount()) {
                menu.hide();
            }
        }
    }
}
