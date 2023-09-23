package kitchenpos.menus.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.menus.tobe.domain.menu.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupLoader menuGroupLoader;
    private final ProductPriceLoader productPriceLoader;
    private final ProfanityPolicy profanityPolicy;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupLoader menuGroupLoader,
            final ProductPriceLoader productPriceLoader,
            final ProfanityPolicy profanityPolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupLoader = menuGroupLoader;
        this.productPriceLoader = productPriceLoader;
        this.profanityPolicy = profanityPolicy;
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest request) {
        final MenuDisplayedName menuDisplayedName = new MenuDisplayedName(request.getName(), profanityPolicy);
        final Price price = new Price(request.getPrice());

        if (!menuGroupLoader.exists(request.getMenuGroupId())) {
            throw new NoSuchElementException();
        }

        // 메뉴 상품 조립
        validateMenuProducts(request);
        List<MenuProduct> menuProductValues = request.getMenuProducts()
                .stream()
                .map(this::fetchMenuProduct)
                .collect(Collectors.toUnmodifiableList());

        final Menu menu = new Menu(
                menuDisplayedName,
                price,
                new MenuGroupId(request.getMenuGroupId()),
                request.isDisplayed(),
                new MenuProducts(menuProductValues)
        );

        menuRepository.save(menu);

        return MenuResponse.fromEntity(menu);
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
    public MenuResponse changePrice(final MenuId menuId, MenuChangePriceRequest request) {
        final Menu menu = findById(menuId);
        menu.changePrice(new Price(request.getPrice()));
        return MenuResponse.fromEntity(menu);
    }

    @Transactional
    public MenuResponse display(final MenuId menuId) {
        final Menu menu = findById(menuId);
        menu.display();
        return MenuResponse.fromEntity(menu);
    }

    @Transactional
    public MenuResponse hide(final MenuId menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return MenuResponse.fromEntity(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        List<Menu> menus = menuRepository.findAll();
        return MenuResponse.fromEntities(menus);
    }

    @Transactional
    public void checkHideAndPrice(ProductId productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        Price productPrice = productPriceLoader.findPriceById(productId.getValue());
        menus.forEach(menu -> menu.fetchProductPrice(productId, productPrice));
    }

    @Transactional(readOnly = true)
    public MenuResponse findMenuById(MenuId menuId) {
        Menu menu = findById(menuId);
        return MenuResponse.fromEntity(menu);
    }

    private Menu findById(MenuId menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
