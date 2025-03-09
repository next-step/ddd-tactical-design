package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.ui.MenuCreateRequest;
import kitchenpos.menus.tobe.ui.MenuPriceChangeRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service("tobeMenuService")
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final ProfanityChecker profanityChecker;
    private final MenuProductService menuProductService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupService menuGroupService,
            final ProfanityChecker profanityChecker,
            final MenuProductService menuProductService
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.profanityChecker = profanityChecker;
        this.menuProductService = menuProductService;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuPrice price = new MenuPrice(request.price());
        final MenuGroup menuGroup = menuGroupService.findById(request.menuGroupId());
        final List<MenuProduct> menuProducts = menuProductService.getMenuProducts(price, request.menuProducts(), request.productIds());
        final MenuName name = new MenuName(request.name(), profanityChecker);
        final Menu menu = new Menu(UUID.randomUUID(), name, price, menuGroup, request.displayed(), menuProducts);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        final MenuPrice price = new MenuPrice(request.price());
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menuProductService.checkMenuProductPrice(menu.getMenuProducts(), price);
        menu.changePrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menuProductService.checkMenuProductPrice(menu.getMenuProducts(), menu.getPrice());
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
