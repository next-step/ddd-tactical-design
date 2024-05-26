package kitchenpos.menus.tobe.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jakarta.transaction.Transactional;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;

@Service
public class DefaultMenuService implements MenuService {
    private final MenuRepository menuRepository;

    public DefaultMenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    @Transactional
    public void hideMenusBasedOnProductPrice(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        if (!CollectionUtils.isEmpty(menus)) {
            menus.forEach(Menu::displayBasedOnProductsPrice);
        }
    }
}
