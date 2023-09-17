package kitchenpos.menus.application;


import kitchenpos.common.domain.DisplayNameChecker;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.application.dto.MenuChangePriceRequest;
import kitchenpos.menus.application.dto.MenuProductCreateRequest;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.domain.MenuProductPriceHandler;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.NOT_EQUAL_MENU_PRODUCT_SIZE;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final DisplayNameChecker displayNameChecker;
    private final ProductQueryService productQueryService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final DisplayNameChecker displayNameChecker,
            final ProductQueryService productQueryService
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.displayNameChecker = displayNameChecker;
        this.productQueryService = productQueryService;
    }

    @Transactional
    public NewMenu create(final MenuCreateRequest request) {
        final NewMenuGroup newMenuGroup = findById(request.getMenuGroupId());
        List<UUID> productIds = getProductIds(request.getMenuProducts());
        NewMenu menu = NewMenu.createMenu(
                productIds, productQueryService::findAllByIdIn, UUID.randomUUID(),
                displayNameChecker, newMenuGroup, request.getName(), request.getPrice(), request.getMenuProducts(), request.isDisplayed()
        );
        return menuRepository.save(menu);
    }

    @Transactional
    public NewMenu changePrice(final UUID menuId, MenuChangePriceRequest request) {
        final NewMenu newMenu = findMenuById(menuId);
        Map<UUID, BigDecimal> productPriceMap = productQueryService.findAllByIdIn(newMenu.getMenuProductIds());
        newMenu.changePrice(productPriceMap, request.getPrice());
        return newMenu;
    }

    @Transactional
    public NewMenu display(final UUID menuId) {
        final NewMenu newMenu = findMenuById(menuId);
        Map<UUID, BigDecimal> productPriceMap = productQueryService.findAllByIdIn(newMenu.getMenuProductIds());
        newMenu.displayed(productPriceMap);
        return newMenu;
    }

    @Transactional
    public NewMenu hide(final UUID menuId) {
        final NewMenu newMenu = findMenuById(menuId);
        newMenu.notDisplayed();
        return newMenu;
    }

    @Transactional(readOnly = true)
    public List<NewMenu> findAll() {
        return menuRepository.findAll();
    }

    private NewMenuGroup findById(UUID id) {
        return menuGroupRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    private NewMenu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    private List<UUID> getProductIds(List<MenuProductCreateRequest> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException(NOT_EQUAL_MENU_PRODUCT_SIZE);
        }
        return menuProducts.stream()
                .map(MenuProductCreateRequest::getProductId)
                .collect(Collectors.toList());
    }

}
