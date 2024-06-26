package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.ProductPrices;
import kitchenpos.products.tobe.application.MenuDisplayService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultMenuDisplayService implements MenuDisplayService {

    private final MenuRepository menuRepository;

    public DefaultMenuDisplayService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void display(UUID productId, ProductPrices productPrices) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (Menu menu : menus) {
            try {
                MenuProducts.of(menu.getMenuProducts(), new Money(menu.getPrice()), productPrices);
            } catch (IllegalArgumentException e) {
                menu.hide();
            }
        }
    }
}
