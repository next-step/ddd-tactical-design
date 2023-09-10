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
    private final PurgomalumClient purgomalumClient;
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient,
        ProductValidator productValidator) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
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

        if (purgomalumClient.containsProfanity(request.getName())) {
            throw new IllegalArgumentException("메뉴의 이름은 비속어를 포함할 수 없습니다");
        }

        Menu menu = Menu.of(request, menuGroup, menuProducts);
        Menu savedMenu = menuRepository.save(menu);
        return new MenuResponse(savedMenu.getId(), savedMenu.getName(), savedMenu.getPrice(), savedMenu.getMenuGroupId(), savedMenu.isDisplayed(), savedMenu.getMenuProductsSeq());
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(request.getPrice());
        return new MenuResponse(menu.getId(), menu.getName(), menu.getPrice(), menu.getMenuGroupId(), menu.isDisplayed(), menu.getMenuProductsSeq());
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId
        ).orElseThrow(NoSuchElementException::new);
        menu.setDisplayable();
        return new MenuResponse(menu.getId(), menu.getName(), menu.getPrice(), menu.getMenuGroupId(), menu.isDisplayed(), menu.getMenuProductsSeq());
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.setHide();
        return new MenuResponse(menu.getId(), menu.getName(), menu.getPrice(), menu.getMenuGroupId(), menu.isDisplayed(), menu.getMenuProductsSeq());
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream()
                .map(it -> new MenuResponse(it.getId(), it.getName(), it.getPrice(), it.getMenuGroupId(), it.isDisplayed(), it.getMenuProductsSeq()))
                .collect(Collectors.toList());
    }
}
