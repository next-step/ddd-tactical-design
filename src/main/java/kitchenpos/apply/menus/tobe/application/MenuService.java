package kitchenpos.apply.menus.tobe.application;

import kitchenpos.apply.menus.tobe.domain.*;
import kitchenpos.apply.menus.tobe.ui.MenuRequest;
import kitchenpos.apply.menus.tobe.ui.MenuResponse;
import kitchenpos.apply.products.tobe.application.ProductValidator;
import kitchenpos.support.domain.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductValidator productValidator;
    private final MenuPriceCalculator menuProductsCalculator;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final PurgomalumClient purgomalumClient,
        final ProductValidator productValidator,
        final MenuPriceCalculator menuProductsCalculator) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
        this.productValidator = productValidator;
        this.menuProductsCalculator = menuProductsCalculator;
    }

    @Transactional
    public MenuResponse create(final MenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        productValidator.isExistProductIn(request);

        final MenuProducts menuProducts = MenuProducts.of(request.getMenuProductRequests());
        final MenuName menuName = new MenuName(request.getName(), purgomalumClient);
        final BigDecimal totalPrice = menuProductsCalculator.getTotalPriceFrom(request);
        final Menu savedMenu = menuRepository.save(Menu.of(menuName, request.getPrice(), menuGroup, request.isDisplayed(), menuProducts, totalPrice));
        return new MenuResponse(savedMenu);
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuRequest request) {
        final Menu menu = menuRepository.findByMenuId(menuId)
                .orElseThrow(NoSuchElementException::new);
        final BigDecimal totalPrice = menuProductsCalculator.getTotalPriceFrom(menu);
        menu.changePrice(request.getPrice(), totalPrice);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findByMenuId(menuId)
                .orElseThrow(NoSuchElementException::new);
        final BigDecimal totalPrice = menuProductsCalculator.getTotalPriceFrom(menu);
        menu.setDisplayable(totalPrice);
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
