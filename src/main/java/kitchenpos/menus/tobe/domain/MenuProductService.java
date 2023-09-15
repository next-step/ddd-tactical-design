package kitchenpos.menus.tobe.domain;

import java.util.List;
import kitchenpos.products.tobe.domain.event.ProductPriceChanged;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuProductService {

    private final MenuProductRepository menuProductRepository;

    public MenuProductService(MenuProductRepository menuProductRepository) {
        this.menuProductRepository = menuProductRepository;
    }

    @EventListener
    @Transactional
    public void checkMenuPrice(ProductPriceChanged event) {
        List<MenuProduct> menuProducts = menuProductRepository.findAllByProductId(event.getProductId());
        for (MenuProduct menuProduct : menuProducts) {
            menuProduct.updatePrice(event.getChangedPrice());
        }

        menuProducts.stream()
            .map(MenuProduct::getMenu)
            .distinct()
            .forEach(Menu::hideIfPriceIsInvalid);
    }
}
