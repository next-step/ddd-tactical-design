package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.application.ProductPrice;

import java.util.List;

public class DefaultMenuDisplayPolicy implements MenuDisplayPolicy {

    private MenuRepository menuRepository;

    public DefaultMenuDisplayPolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void display(ProductPrice productPrice) {
        List<Menu> menus = menuRepository.findAllByProductId(productPrice.productId());
        for (Menu menu : menus) {
            menu.changeProductPrice(productPrice.productId(), productPrice.money());
        }
    }
}
