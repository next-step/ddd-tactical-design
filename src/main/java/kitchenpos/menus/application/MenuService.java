package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuPriceChangeRequest;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.application.dto.MenuProductCreateRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final MenuProductFactory menuProductFactory;
    private final MenuDisplayedNamePolicy menuDisplayedNamePolicy;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final MenuProductFactory menuProductFactory,
        final MenuDisplayedNamePolicy menuDisplayedNamePolicy
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.menuProductFactory = menuProductFactory;
        this.menuDisplayedNamePolicy = menuDisplayedNamePolicy;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final MenuPrice price = MenuPrice.from(request.getPrice());
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final List<MenuProductCreateRequest> menuProductRequests = request.getMenuProducts();
        this.validateMenuProducts(menuProductRequests);
        final List<MenuProduct> menuProducts = menuProductRequests.stream()
                .map(it -> menuProductFactory.create(it.getProductId(), it.getQuantity()))
                .collect(Collectors.toList());
        final MenuDisplayedName displayName = MenuDisplayedName.from(request.getName(), menuDisplayedNamePolicy);
        final Menu menu = new Menu(
                UUID.randomUUID(),
                displayName,
                price,
                menuGroup,
                request.isDisplayed(),
                new MenuProducts(menuProducts)
        );
        return menuRepository.save(menu);
    }

    private void validateMenuProducts(final List<MenuProductCreateRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<UUID> productIds = menuProductRequests.stream()
                .map(MenuProductCreateRequest::getProductId)
                .collect(Collectors.toList());
        final List<Product> products = productRepository.findAllByIdIn(productIds);
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        final MenuPrice price = MenuPrice.from(request.getPrice());
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.changePrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
