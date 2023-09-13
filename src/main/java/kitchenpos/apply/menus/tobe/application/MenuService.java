package kitchenpos.apply.menus.tobe.application;

import kitchenpos.apply.menus.tobe.domain.*;
import kitchenpos.apply.menus.tobe.ui.MenuRequest;
import kitchenpos.apply.menus.tobe.ui.MenuResponse;
import kitchenpos.apply.products.tobe.application.ProductValidator;
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
    private final MenuPriceChecker menuPriceChecker;
    private final ProductValidator productValidator;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final MenuPriceChecker menuPriceChecker,
        final PurgomalumClient purgomalumClient,
        ProductValidator productValidator) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuPriceChecker = menuPriceChecker;
        this.purgomalumClient = purgomalumClient;
        this.productValidator = productValidator;
    }

    @Transactional
    public MenuResponse create(final MenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        productValidator.isExistProductIn(request);

        final MenuProducts menuProducts = MenuProducts.of(request.getMenuProductRequests());
        final MenuName menuName = new MenuName(request.getName(), purgomalumClient);
        Menu savedMenu = menuRepository.save(Menu.of(menuName, request.getPrice(), menuGroup, request.isDisplayed(), menuProducts, menuPriceChecker));
        return new MenuResponse(savedMenu);
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(request.getPrice(), menuPriceChecker);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.setDisplayable(menuPriceChecker);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.setHide();
        return new MenuResponse(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }
}
