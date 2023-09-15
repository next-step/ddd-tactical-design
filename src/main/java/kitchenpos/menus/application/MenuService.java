package kitchenpos.menus.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final ProductPriceLoader productPriceLoader;
    private final ProfanityPolicy profanityPolicy;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupService menuGroupService,
            final ProductPriceLoader productPriceLoader,
            final ProfanityPolicy profanityPolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productPriceLoader = productPriceLoader;
        this.profanityPolicy = profanityPolicy;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuDisplayedName menuDisplayedName = new MenuDisplayedName(request.getName(), profanityPolicy);
        final Price price = new Price(request.getPrice());
        final MenuGroup menuGroup = menuGroupService.findById(request.getMenuGroupId());

        // 메뉴 상품 조립
        validateMenuProducts(request);
        List<MenuProduct> menuProductValues = request.getMenuProducts()
                .stream()
                .map(this::fetchMenuProduct)
                .collect(Collectors.toUnmodifiableList());

        final Menu menu = new Menu(
                menuDisplayedName,
                price,
                menuGroup,
                request.isDisplayed(),
                new MenuProducts(menuProductValues)
        );
        return menuRepository.save(menu);
    }

    private MenuProduct fetchMenuProduct(MenuProductRequest menuProductRequest) {
        Price productPrice = productPriceLoader.findPriceById(menuProductRequest.getProductId());
        return new MenuProduct(menuProductRequest.getProductId(), productPrice, menuProductRequest.getQuantity());
    }

    private void validateMenuProducts(MenuCreateRequest request) {
        List<MenuProductRequest> menuProducts = request.getMenuProducts();

        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new MenuProductException(MenuErrorCode.MENU_PRODUCT_IS_EMPTY);
        }

    }

    @Transactional
    public Menu changePrice(final MenuId menuId, MenuChangePriceRequest request) {

        final Menu menu = findById(menuId);
        menu.changePrice(new Price(request.getPrice()));

        return menu;
    }

    @Transactional
    public Menu display(final MenuId menuId) {
        final Menu menu = findById(menuId);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final MenuId menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Transactional
    public void checkHideAndPrice(ProductId productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        Price productPrice = productPriceLoader.findPriceById(productId.getValue());
        menus.forEach(menu -> menu.fetchProductPrice(productId, productPrice));
    }

    @Transactional(readOnly = true)
    public Menu findById(MenuId menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
