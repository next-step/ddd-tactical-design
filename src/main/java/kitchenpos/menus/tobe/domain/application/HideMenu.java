package kitchenpos.menus.tobe.domain.application;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface HideMenu {
    Menu execute(UUID menuId);
}

@Service
class DefaultHideMenu implements HideMenu {
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    public DefaultHideMenu(MenuRepository menuRepository, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Override
    public final Menu execute(UUID menuId) {
        final Menu menu = menuRepository.findMenuById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.hide();
        menuRepository.saveMenu(menu);
        return menu;
    }
}
