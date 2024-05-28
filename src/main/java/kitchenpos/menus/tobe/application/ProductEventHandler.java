package kitchenpos.menus.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductEventHandler {

    private final MenuRepository menuRepository;

    public ProductEventHandler(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void productPriceChangeEvent(UUID productId) {
        menuRepository
                .findAllByProductId(productId)
                .forEach(Menu::updateDisplayStatusOnPriceChange);
    }

}
