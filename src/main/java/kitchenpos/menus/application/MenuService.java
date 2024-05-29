package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuName;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.ProductClient;
import kitchenpos.menus.tobe.domain.ProfanityChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductClient productClient;
    private final ProfanityChecker profanityChecker;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductClient productClient,
        final ProfanityChecker profanityChecker
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productClient = productClient;
        this.profanityChecker = profanityChecker;
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest request) {
        final Menu menu = Menu.from(
                MenuName.from(request.name(), profanityChecker),
                MenuPrice.from(request.price()),
                getMenuGroup(request.menuGroupId()),
                request.displayed(),
                MenuProducts.from(request.menuProducts(), productClient)
        );
        return MenuResponse.of(menuRepository.save(menu));
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final MenuPrice price = MenuPrice.from(request.price());
        final Menu menu = getMenu(menuId);
        menu.changePrice(price);
        return MenuResponse.of(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = getMenu(menuId);
        menu.display();
        return MenuResponse.of(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = getMenu(menuId);
        menu.hide();
        return MenuResponse.of(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream()
                .map(MenuResponse::of)
                .toList();
    }

    @Transactional
    public void hideMenuBasedOnProductPrice(UUID productId, BigDecimal productPrice) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(menu -> menu.changeMenuProductPrice(productId, productPrice));
        menus.stream()
                .filter(Menu::isPriceGreaterThanMenuProductsSum)
                .forEach(Menu::hide);
    }

    private Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    private MenuGroup getMenuGroup(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }
}
