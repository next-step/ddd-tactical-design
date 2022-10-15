package kitchenpos.menus.application;

import kitchenpos.menus.domain.*;
import kitchenpos.menus.model.MenuGroupModel;
import kitchenpos.menus.model.MenuModel;
import kitchenpos.menus.model.MenuProductModel;
import kitchenpos.menus.model.MenuProductRequest;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
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

        final List<MenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProductRequest menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.quantity();
            MenuProductQuantity menuProductQuantity = new MenuProductQuantity(quantity);
            final Product product = productRepository.findById(menuProductRequest.productId())
                .orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                product.priceValue()
                    .multiply(BigDecimal.valueOf(quantity))
            );
            menuProducts.add(new MenuProduct(product, menuProductQuantity));
        }

        MenuPrice menuPrice = new MenuPrice(price);
        menuPrice.compareProductsPrice(sum);
        MenuName menuName = new MenuName(name, purgomalumClient);
        final Menu menu = new Menu(menuName, menuPrice, menuGroup, isDisplayed, new MenuProducts(menuProducts));
        return createMenuModel(menuRepository.save(menu));
    }
    @Transactional
    public MenuModel changePrice(final UUID menuId, final BigDecimal price) {
        MenuPrice menuPrice = new MenuPrice(price);
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menuPrice.compareProductsPrice(menu.menuProductPriceSum());
        menu.updatePrice(new MenuPrice(price));
        return createMenuModel(menu);
    }

    @Transactional
    public MenuModel display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.displayAvailabilityCheck(menu.menuProductPriceSum());
        menu.setDisplayed(true);
        return createMenuModel(menu);
    }

    @Transactional
    public MenuModel hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.setDisplayed(false);
        return createMenuModel(menu);
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

    private MenuModel createMenuModel(Menu menu) {
        MenuGroupModel menuGroupModel = new MenuGroupModel(menu.menuGroup().id(), menu.menuGroup().nameValue());
        List<MenuProductModel> menuProductModels = menu.menuProducts()
                .menuProducts().stream()
                .map(menuProduct -> new MenuProductModel(menuProduct.seq(), menuProduct.quantityValue(), menuProduct.productId()))
                .collect(Collectors.toList());
        return new MenuModel(menu.id(), menu.nameValue(), menu.priceValue(), menuGroupModel, menu.isDisplayed(), menuProductModels, menu.menuGroupId());
    }
}
