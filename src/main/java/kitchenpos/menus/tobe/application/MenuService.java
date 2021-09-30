package kitchenpos.menus.tobe.application;

import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.ui.dto.MenuChangePriceRequest;
import kitchenpos.menus.tobe.ui.dto.MenuCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuProductLoader menuProductLoader;
    private final MenuProductClient menuProductClient;
    private final MenuGroupService menuGroupService;
    private final Profanities profanities;

    public MenuService(final MenuRepository menuRepository, final MenuProductLoader menuProductLoader, final MenuProductClient menuProductClient, final MenuGroupService menuGroupService, final Profanities profanities) {
        this.menuRepository = menuRepository;
        this.menuProductLoader = menuProductLoader;
        this.menuProductClient = menuProductClient;
        this.menuGroupService = menuGroupService;
        this.profanities = profanities;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuGroup menuGroup = loadMenuGroup(request.getMenuGroupId());
        final MenuProducts menuProducts = loadMenuProducts(request.getMenuProducts1());
        return menuRepository.save(new Menu(
                request.getName(profanities),
                request.getPrice(),
                menuGroup,
                request.isDisplayed(),
                menuProducts));
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = findById(menuId);
        menu.changePrice(request.price());
        return menu;
    }

    @Transactional
    public void checkAllHideByProductId(final UUID productId) {
        final List<Menu> menus = findAllByProductId(productId);
        menus.forEach(Menu::checkHide);
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        final List<Menu> menus = menuRepository.findAll();
        menus.forEach(menu -> loadMenuProducts(menu.getMenuProducts()));
        return menus;
    }

    @Transactional(readOnly = true)
    public Menu findById(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        loadMenuProducts(menu.getMenuProducts());
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllByProductId(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(menu -> loadMenuProducts(menu.getMenuProducts()));
        return menus;
    }

    private MenuGroup loadMenuGroup(final UUID menuGroupId) {
        return menuGroupService.findById(menuGroupId);
    }

    private MenuProducts loadMenuProducts(final List<MenuProduct> menuProducts) {
        final List<UUID> productIds = menuProducts.stream().map(MenuProduct::getProductId).collect(toList());
        final List<ProductVO> products = menuProductClient.findAllByIdn(productIds);
        return menuProductLoader.loadMenuProducts(menuProducts, products);
    }
}
