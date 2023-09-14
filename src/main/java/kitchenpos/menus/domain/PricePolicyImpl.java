package kitchenpos.menus.domain;

import kitchenpos.common.DomainService;
import kitchenpos.products.tobe.domain.PricePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.util.List;

@DomainService
public class PricePolicyImpl implements PricePolicy {
    private final MenuRepository menuRepository;

    public PricePolicyImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void changePrice(Product product, ProductPrice price) {
        product.changePrice(price);
        final List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
            menu.changeMenuProductPrice(product);
            menuRepository.save(menu);
        }
        menuRepository.saveAll(menus);
    }
}
