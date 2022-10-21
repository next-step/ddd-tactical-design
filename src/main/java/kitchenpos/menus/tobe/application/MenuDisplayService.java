package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuDisplayService {

    private final MenuRepository menuRepository;

    public MenuDisplayService(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findMenu(menuId);
        menu.hide();
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findMenu(menuId);
        menu.display();
        return menu;
    }

    private Menu findMenu(UUID menuId) {
        return menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
    }
}
