package kitchenpos.menus.application.menu;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.common.domain.client.PurgomalumClient;
import kitchenpos.menus.application.menu.dto.MenuRequest;
import kitchenpos.menus.application.menu.dto.MenuResponse;
import kitchenpos.menus.domain.menu.Menu;
import kitchenpos.menus.domain.menu.MenuProducts;
import kitchenpos.menus.domain.menu.MenuRepository;
import kitchenpos.menus.domain.menu.ProductClient;
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
    private final PurgomalumClient purgomalumClient;
    private final ProductClient productClient;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final PurgomalumClient purgomalumClient,
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
        final Name menuName = Name.of(request.getName(), purgomalumClient);
        final Price menuPrice = Price.of(request.getPrice());
        final MenuProducts menuProducts = MenuProducts.of(request.getMenuProductsRequests().stream()
                .map(p -> p.toMenuProduct(productClient.getProductPrice(p.getProductId())))
                .collect(Collectors.toList()));

        if (productClient.isInvalidMenuProductsCount(menuProducts.getMenuProducts())) {
            throw new IllegalArgumentException("메뉴에 등록되지 않은 상품이 있습니다.");
        }

        final Menu menu = new Menu(menuGroup, menuName, menuPrice, request.isDisplayed(), menuProducts);
        return new MenuResponse(menuRepository.save(menu));
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuRequest request) {
        final Menu menu = getMenu(menuId);
        final Price menuPrice = Price.of(request.getPrice());
        menu.changePrice(menuPrice);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = getMenu(menuId);
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
    public void hideIfMenuPriceGraterThanProducts(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            // refresh product price
            menu.getMenuProducts().forEach(menuProduct ->
                    menuProduct.changeProductPrice(productClient.getProductPrice(menuProduct.getProductId()))
            );

            menu.hideIfMenuPriceGraterThanProducts();
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
