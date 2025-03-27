package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class MenuFacade {
    private final MenuService menuService;
    private final MenuGroupService menuGroupService;
    private final ProductClient productClient;
    private final ProfanityChecker profanityChecker;


    public MenuFacade(MenuService menuService, MenuGroupService menuGroupService, ProductClient productClient, ProfanityChecker profanityChecker) {
        this.menuService = menuService;
        this.menuGroupService = menuGroupService;
        this.productClient = productClient;
        this.profanityChecker = profanityChecker;
    }

    @Transactional
    public Menu create(MenuCreateRequest request) {
        final MenuName name = new MenuName(request.name(), profanityChecker);
        final MenuPrice price = new MenuPrice(request.price());
        final MenuGroup menuGroup = menuGroupService.findById(request.menuGroupId());
        final ProductInfos productInfos = productClient.listByIds(request.productIds());
        final MenuProductCatalog menuProductCatalog = new MenuProductCatalog(request.menuProducts(), productInfos);
        return menuService.create(name, price, menuGroup, request.displayed(), menuProductCatalog);
    }

    @Transactional
    public Menu changePrice(UUID menuId, MenuPriceChangeRequest request) {
        final MenuPrice price = new MenuPrice(request.price());
        final Menu menu = menuService.findById(menuId);
        final ProductInfos productInfos = productClient.listByIds(menu.findProductIds());
        return menuService.changePrice(menuId, price, productInfos);
    }

    @Transactional
    public Menu display(UUID menuId) {
        final Menu menu = menuService.findById(menuId);
        final ProductInfos productInfos = productClient.listByIds(menu.findProductIds());
        return menuService.display(menuId, productInfos);
    }
}
