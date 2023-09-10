package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final ProductService productService;
    private final ProfanityPolicy profanityPolicy;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupService menuGroupService,
            final ProductService productService,
            final ProfanityPolicy profanityPolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productService = productService;
        this.profanityPolicy = profanityPolicy;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuDisplayedName menuDisplayedName = new MenuDisplayedName(request.getName(), profanityPolicy);
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());
        final MenuGroup menuGroup = menuGroupService.findById(request.getMenuGroupId());


        // 메뉴 상품 조립
        validateMenuProducts(request);
        List<MenuProduct> menuProductValues = request.getMenuProducts()
                .stream()
                .map(this::menuProductRequestToEntity)
                .collect(Collectors.toUnmodifiableList());

        final Menu menu = new Menu(
                menuDisplayedName,
                menuPrice,
                menuGroup,
                request.isDisplayed(),
                new MenuProducts(menuProductValues)
        );

        return menuRepository.save(menu);
    }

    private void validateMenuProducts(MenuCreateRequest request) {
        List<MenuProductRequest> menuProducts = request.getMenuProducts();

        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new MenuProductException(MenuErrorCode.MENU_PRODUCT_IS_EMPTY);
        }

    }

    @Transactional
    public Menu changePrice(final UUID menuId, MenuChangePriceRequest request) {

        final Menu menu = findById(menuId);
        menu.changePrice(new MenuPrice(request.getPrice()));

        return menu;
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
        return menuRepository.findAll();
    }

    @Transactional
    public void hideMenuWhenChangeProductPrice(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(Menu::hideWhenPriceGreaterThanProducts);
    }

    @Transactional(readOnly = true)
    public Menu findById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    private MenuProduct menuProductRequestToEntity(MenuProductRequest menuProductRequest) {
        MenuProductQuantity menuProductQuantity = new MenuProductQuantity(menuProductRequest.getQuantity());
        Product product = productService.findById(menuProductRequest.getProductId());
        return new MenuProduct(product, menuProductQuantity);
    }
}
