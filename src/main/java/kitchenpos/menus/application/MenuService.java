package kitchenpos.menus.application;

import kitchenpos.menus.domain.*;
import kitchenpos.menus.model.MenuGroupModel;
import kitchenpos.menus.model.MenuModel;
import kitchenpos.menus.model.MenuProductModel;
import kitchenpos.menus.model.MenuProductRequest;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuModel create(final BigDecimal price, final UUID menuGroupId, final String name,
                            final boolean isDisplayed, final List<MenuProductRequest> menuProductRequests) {

        final MenuGroup menuGroup = menuGroupRepository.findById(menuGroupId)
            .orElseThrow(NoSuchElementException::new);

        validateProductRequests(menuProductRequests);

        MenuProducts menuProducts = createMenuProducts(menuProductRequests);
        MenuPrice menuPrice = new MenuPrice(price, menuProducts.menuProductPriceSum());
        MenuName menuName = new MenuName(name, purgomalumClient);
        final Menu menu = new Menu(menuName, menuPrice, menuGroup, isDisplayed, menuProducts);
        return new MenuModel(menuRepository.save(menu));
    }

    @Transactional
    public MenuModel changePrice(final UUID menuId, final BigDecimal price) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal productsPriceSum = menu.menuProductPriceSum();
        MenuPrice menuPrice = new MenuPrice(price, productsPriceSum);
        menu.updatePrice(menuPrice);
        return new MenuModel(menu);
    }
    @Transactional
    public MenuModel display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.displayAvailabilityCheck(menu.menuProductPriceSum());
        menu.setDisplayed(true);
        return new MenuModel(menu);
    }

    @Transactional
    public MenuModel hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.setDisplayed(false);
        return new MenuModel(menu);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    private void validateProductRequests(List<MenuProductRequest> menuProductRequests) {

        if (CollectionUtils.isEmpty(menuProductRequests)) {
            throw new IllegalArgumentException();
        }

        List<Product> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                        .map(MenuProductRequest::productId)
                        .collect(Collectors.toList())
        );

        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
    }

    private MenuProducts createMenuProducts(final List<MenuProductRequest> menuProductRequests) {
        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (final MenuProductRequest menuProductRequest : menuProductRequests) {
            MenuProductQuantity menuProductQuantity = new MenuProductQuantity(menuProductRequest.quantity());
            final Product product = productRepository.findById(menuProductRequest.productId())
                    .orElseThrow(NoSuchElementException::new);
            menuProducts.add(new MenuProduct(product, menuProductQuantity));
        }

        return new MenuProducts(menuProducts);
    }
}
