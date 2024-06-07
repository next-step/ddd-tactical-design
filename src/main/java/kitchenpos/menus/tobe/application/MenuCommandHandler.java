package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.application.ChangeMenuPrice;
import kitchenpos.menus.tobe.domain.application.CreateMenu;
import kitchenpos.menus.tobe.domain.application.DisplayMenu;
import kitchenpos.menus.tobe.domain.application.HideMenu;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.dto.MenuChangePriceDto;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MenuCommandHandler {
    private final MenuRepository menuRepository;
    private final CreateMenu createMenu;
    private final ChangeMenuPrice changeMenuPrice;
    private final DisplayMenu displayMenu;
    private final HideMenu hideMenu;

    public MenuCommandHandler(MenuRepository menuRepository,
            CreateMenu createMenu,
            ChangeMenuPrice changeMenuPrice,
            DisplayMenu displayMenu,
            HideMenu hideMenu) {
        this.menuRepository = menuRepository;
        this.createMenu = createMenu;
        this.changeMenuPrice = changeMenuPrice;
        this.displayMenu = displayMenu;
        this.hideMenu = hideMenu;
    }

    @Transactional
    public Menu create(final MenuCreateDto request) {
        return createMenu.execute(request);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuChangePriceDto request) {
        return changeMenuPrice.execute(menuId, request);
    }

    @Transactional
    public Menu display(final UUID menuId) {
        return displayMenu.execute(menuId);
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        return hideMenu.execute(menuId);
    }

}
