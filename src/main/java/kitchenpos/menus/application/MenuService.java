package kitchenpos.menus.application;


import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.domain.MenuProductPriceHandler;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.common.domain.DisplayNameChecker;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.NOT_EQUAL_MENU_PRODUCT_SIZE;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final DisplayNameChecker displayNameChecker;
    private final MenuProductPriceHandler menuProductPriceHandler;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            final DisplayNameChecker displayNameChecker,
            final MenuProductPriceHandler menuProductPriceHandler
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.displayNameChecker = displayNameChecker;
        this.menuProductPriceHandler = menuProductPriceHandler;
    }

    @Transactional
    public NewMenu create(final MenuCreateRequest request) {
        final NewMenuGroup newMenuGroup = findById(request.getMenuGroupId());
        checkExistProduct(request);
        MenuProducts menuProducts = MenuProducts.create(request.getMenuProducts());
        checkPrice(request.getProductIds(), Price.of(request.getPrice()), menuProducts);
        NewMenu newMenu = NewMenu.create(
                UUID.randomUUID(),
                newMenuGroup,
                menuProducts,
                Price.of(request.getPrice()),
                DisplayedName.of(request.getName(), displayNameChecker),
                request.isDisplayed());
        return menuRepository.save(newMenu);
    }

    @Transactional
    public NewMenu changePrice(final UUID menuId, MenuChangePriceRequest request) {
        final NewMenu newMenu = findMenuById(menuId);
        checkPrice(newMenu.getMenuProductIds(), Price.of(request.getPrice()), newMenu.getMenuProducts());
        newMenu.changePrice(request.getPrice());
        return newMenu;
    }

    @Transactional
    public NewMenu display(final UUID menuId) {
        final NewMenu newMenu = findMenuById(menuId);
        checkPrice(newMenu.getMenuProductIds(), Price.of(newMenu.getPrice()), newMenu.getMenuProducts());
        newMenu.displayed();
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

    private void checkPrice(List<UUID> productIds, Price price, MenuProducts menuProducts) {
        Map<UUID, Product> productMap = productRepository.findAllByIdIn(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
        menuProductPriceHandler.checkPrice(productMap, price, menuProducts.getMenuProducts());
    }

    private NewMenuGroup findById(UUID id) {
        return menuGroupRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    private NewMenu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    private void checkExistProduct(MenuCreateRequest request) {
        List<UUID> productIds = request.getProductIds();
        Map<UUID, Product> productMap = productRepository.findAllByIdIn(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
        if (productIds.size() != productMap.size()) {
            throw new IllegalArgumentException(NOT_EQUAL_MENU_PRODUCT_SIZE);
        }
    }
}
