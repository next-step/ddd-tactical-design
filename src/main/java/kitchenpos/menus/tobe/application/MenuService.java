package kitchenpos.menus.tobe.application;

import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.ui.dto.MenuChangePriceRequest;
import kitchenpos.menus.tobe.ui.dto.MenuCreateRequest;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuProductClient menuProductClient;
    private final MenuGroupService menuGroupService;
    private final Profanities profanities;

    public MenuService(final MenuRepository menuRepository, final MenuProductClient menuProductClient, final MenuGroupService menuGroupService, final Profanities profanities) {
        this.menuRepository = menuRepository;
        this.menuProductClient = menuProductClient;
        this.menuGroupService = menuGroupService;
        this.profanities = profanities;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuGroup menuGroup = loadMenuGroup(request.getMenuGroupId());
        final MenuProducts menuProducts = loadMenuProducts(request.getMenuProducts());
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
        loadMenuProducts(menu.getMenuProducts());
        menu.changePrice(request.price());
        return menu;
    }

    @Transactional
    public void checkAllHideByProductId(final UUID productId) {
        final List<Menu> menus = findAllByProductId(productId);
        menus.forEach(menu -> loadMenuProducts(menu.getMenuProducts()));
        menus.forEach(Menu::checkHide);
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findById(menuId);
        loadMenuProducts(menu.getMenuProducts());
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
        return menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Menu findById(final UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllByProductId(final UUID productId) {
        return menuRepository.findAllByProductId(productId);
    }

    private MenuGroup loadMenuGroup(final UUID menuGroupId) {
        return menuGroupService.findById(menuGroupId);
    }

    private MenuProducts loadMenuProducts(final MenuProducts menuProducts) {
        final List<UUID> productIds = menuProducts.getProductIds();
        final List<Product> products = menuProductClient.findAllByIdIn(productIds);
        menuProducts.validateRegistered(products);
        menuProducts.loadProducts(products);
        return menuProducts;
    }
}
