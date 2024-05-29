package kitchenpos.menus.application;

import kitchenpos.common.infra.PurgomalumClient;
import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductCreateRequest;
import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.ProductClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductClient productClient;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient,
        final ProductClient productClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productClient = productClient;
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest request) {
        final BigDecimal price = request.price();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final MenuGroup menuGroup = menuGroupRepository.findById(request.menuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final List<MenuProductCreateRequest> menuProductRequests = request.menuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
            menuProductRequests.stream()
                .map(MenuProductCreateRequest::productId)
                .toList()
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<MenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProductCreateRequest menuProductRequest : menuProductRequests) {
            final MenuProduct menuProduct = MenuProduct.from(menuProductRequest.productId(), menuProductRequest.quantity(), productClient);
            sum = sum.add(menuProduct.totalPrice());
            menuProducts.add(menuProduct);
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.name();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroup(menuGroup);
        menu.setDisplayed(request.displayed());
        menu.setMenuProducts(menuProducts);
        return MenuResponse.of(menuRepository.save(menu));
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final BigDecimal price = request.price();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = menu.sumMenuProducts();
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.setPrice(price);
        return MenuResponse.of(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        if (menu.isPriceGreaterThanMenuProductsSum()) {
            throw new IllegalStateException();
        }
        menu.setDisplayed(true);
        return MenuResponse.of(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
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
    public void hideMenuBasedOnProductPrice(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.stream()
                .filter(Menu::isPriceGreaterThanMenuProductsSum)
                .forEach(Menu::hide);
    }
}
