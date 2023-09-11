package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.ui.MenuProductRequest;
import kitchenpos.menus.tobe.ui.MenuRequest;
import kitchenpos.menus.tobe.ui.MenuResponse;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.application.ProductValidator;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuNameFactory menuNameFactory;
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final MenuNameFactory menuNameFactory,
        ProductValidator productValidator) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.menuNameFactory = menuNameFactory;
        this.productValidator = productValidator;
    }

    @Transactional
    public MenuResponse create(final MenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final List<UUID> productIds = request.getMenuProductRequests().stream()
                .map(MenuProductRequest::getProductId)
                .collect(Collectors.toList());

        productValidator.isExistProductIn(productIds);

        final Map<String, Product> productMap = productRepository.findAllByIdIn(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        final MenuProducts menuProducts = MenuProducts.of(request, productMap);
        final MenuName menuName = menuNameFactory.createMenuName(request.getName());

        Menu savedMenu = menuRepository.save(Menu.of(menuName, request.getPrice(), menuGroup, request.isDisplayed(), menuProducts));
        return new MenuResponse(savedMenu);
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(request.getPrice());
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId
        ).orElseThrow(NoSuchElementException::new);
        menu.setDisplayable();
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
