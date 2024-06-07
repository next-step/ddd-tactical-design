package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface HideMenu {
    Menu execute(UUID menuId);
}

@Service
class DefaultHideMenu implements HideMenu {
    private final MenuRepository menuRepository;

    public DefaultHideMenu(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public final Menu execute(UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        menu.setDisplayed(false);
        return menu;
    }
}
