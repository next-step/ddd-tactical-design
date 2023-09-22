package kitchenpos.menus.application.menu;

import kitchenpos.menus.application.menu.dto.MenuProductRequest;
import kitchenpos.menus.application.menu.dto.MenuRequest;
import kitchenpos.menus.application.menu.dto.MenuResponse;
import kitchenpos.menus.domain.menu.*;
import kitchenpos.menus.domain.menugroup.MenuGroup;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuPurgomalumClient purgomalumClient;
    private final ProductClient productClient;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final MenuPurgomalumClient purgomalumClient,
            final ProductClient productClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
        this.productClient = productClient;
    }

    @Transactional
    public MenuResponse create(final MenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴그룹입니다."));
        final MenuName menuName = MenuName.of(request.getName(), purgomalumClient);
        final MenuPrice menuPrice = MenuPrice.of(request.getPrice());
        final List<MenuProductRequest> menuProductRequests = request.getMenuProductsRequests();
        if (menuProductRequests == null || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException("메뉴의 상품정보가 비어 있습니다.");
        }
        final List<MenuProduct> menuProducts = menuProductRequests.stream()
                .map(p -> p.toMenuProduct(productClient.getProductPrice(p.getProductId())))
                .collect(Collectors.toList());

        productClient.validateMenuProducts(menuProducts, MenuPrice.of(request.getPrice()));

        final Menu menu = new Menu(menuGroup, menuName, menuPrice, request.isDisplayed(), menuProducts);
        return new MenuResponse(menuRepository.save(menu));
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuRequest request) {
        final Menu menu = getMenu(menuId);

        MenuPrice menuPrice = MenuPrice.of(request.getPrice());
        productClient.validateMenuPrice(menu.getMenuProducts(), menuPrice);

        menu.changePrice(menuPrice);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = getMenu(menuId);
        try {
            productClient.validateMenuPrice(menu.getMenuProducts(), menu.getMenuPrice());
        } catch (final IllegalArgumentException e) {
            throw new IllegalStateException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없습니다.");
        }
        menu.display();
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = getMenu(menuId);
        menu.hide();
        return new MenuResponse(menu);
    }

    @Transactional
    public void hideIfMenuPriceGraterThanProduct(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            // refresh product price
            menu.getMenuProducts().forEach(menuProduct ->
                    menuProduct.changeProductPrice(productClient.getProductPrice(menuProduct.getProductId()))
            );

            try {
                productClient.validateMenuPrice(menu.getMenuProducts(), menu.getMenuPrice());
            } catch (final IllegalArgumentException e) {
                menu.hide();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }

    private Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴입니다."));
    }
}
