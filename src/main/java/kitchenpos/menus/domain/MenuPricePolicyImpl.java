package kitchenpos.menus.domain;

import kitchenpos.common.DomainService;
import kitchenpos.products.tobe.domain.MenuPricePolicy;
import kitchenpos.products.tobe.domain.Product;

import java.util.List;

@DomainService
public class MenuPricePolicyImpl implements MenuPricePolicy {
    private final MenuRepository menuRepository;

    public MenuPricePolicyImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void changeMenuProductPrice(Product product) {
        final List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
            menu.changeMenuProductPrice(product);
        }
    }
}
