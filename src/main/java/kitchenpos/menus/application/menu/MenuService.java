package kitchenpos.menus.application.menu;

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
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴그룹입니다. ["+ request.getMenuGroupId().toString() +"]"));

        final MenuName menuName = MenuName.of(request.getName(), purgomalumClient);

        final List<MenuProduct> menuProducts = request.getMenuProducts();
        productClient.validateMenuProducts(menuProducts, MenuPrice.of(request.getPrice()));

        final Menu menu = new Menu(menuGroup, menuName, request.getPrice(), request.isDisplayed(), menuProducts);
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
    public void changeDisplayed(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            try {
                productClient.validateMenuPrice(menu.getMenuProducts(), menu.getMenuPrice());
            } catch (final IllegalArgumentException e) {
                menu.setDisplayed(false);
            }
        }
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = getMenu(menuId);
        productClient.validateMenuPrice(menu.getMenuProducts(), menu.getMenuPrice());
        menu.setDisplayed(true);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = getMenu(menuId);
        menu.setDisplayed(false);
        return new MenuResponse(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }

    private Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴입니다. ["+ menuId.toString() +"]"));
    }
}
