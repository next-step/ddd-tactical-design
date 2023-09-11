package kitchenpos.menus.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.menus.infra.DefaultMenuProductMappingService;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final DefaultMenuProductMappingService mappingService;
    private final ProfanityPolicy profanityPolicy;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupService menuGroupService,
            final DefaultMenuProductMappingService mappingService,
            final ProfanityPolicy profanityPolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.mappingService = mappingService;
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
        Product product = mappingService.findById(menuProductRequest.getProductId());
        return new MenuProduct(product.getId(), product.getPrice(), menuProductRequest.getQuantity());
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
        menu.changePrice(new Price(request.getPrice()));

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
    public void checkHideAndPrice(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(menu -> fetchMenuProduct(productId, menu));
        menus.forEach(Menu::checkPriceAndHide);
    }

    private void fetchMenuProduct(UUID productId, Menu menu) {
        Product product = mappingService.findById(productId);
        menu.getMenuProducts()
                .getValues()
                .stream()
                .filter(menuProduct -> menuProduct.getProductId().equals(productId))
                .forEach(menuProduct -> menuProduct.fetchPrice(product.getPrice()));
    }

    @Transactional(readOnly = true)
    public Menu findById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
