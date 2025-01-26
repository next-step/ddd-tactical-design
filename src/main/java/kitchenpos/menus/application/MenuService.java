package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.application.dto.MenuPriceChangeRequest;
import kitchenpos.menus.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuAntiCorruptionService antiCorruptionService;
    private final PurgomalumClient purgomalumClient;

    public MenuService(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository, MenuAntiCorruptionService antiCorruptionService, PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.antiCorruptionService = antiCorruptionService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final var menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NotFoundMenuException::new);
        final var menuProducts = antiCorruptionService.createMenuProducts(request.getMenuProducts());
        final var menu = Menu.newOne(request.getName(), request.getPrice(), request.isDisplayed(), menuGroup, menuProducts, purgomalumClient);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        menu.updateMenuPrice(request.getPrice());
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Transactional
    public void changeProductPrice(final UUID productId, final BigDecimal productPrice) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            menu.updateMenuProductPrice(productId, productPrice);
        }
    }
}
