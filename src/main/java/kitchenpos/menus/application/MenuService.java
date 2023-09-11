package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.domain.exception.NotFoundMenuException;
import kitchenpos.menus.vo.MenuProducts;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.Products;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final ProductService productService;
    private final PurgomalumClient purgomalumClient;


    private final MenuGroupService menuGroupService;

    public MenuService(
            final MenuRepository menuRepository,
            final ProductService productService,
            final PurgomalumClient purgomalumClient,
            final MenuGroupService menuGroupService) {
        this.menuRepository = menuRepository;
        this.productService = productService;
        this.purgomalumClient = purgomalumClient;
        this.menuGroupService = menuGroupService;
    }

    @Transactional
    public Menu create(final Menu request) {
        final BigDecimal price = request.getPrice();
        MenuGroup menuGroup = this.menuGroupService.findById(request.getMenuGroupId());

        MenuProducts menuProductRequests = request.getMenuProducts();
        Products products = productService.findAllByIdIn(menuProductRequests);

        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }


        final List<MenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProductRequest : menuProductRequests.getMenuProducts()) {
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productService.findById(menuProductRequest.getProductId());

            sum = sum.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(quantity))
            );
            final MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProduct(product);
            menuProduct.setQuantity(quantity);
            menuProducts.add(menuProduct);
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }

        Menu menu = new Menu(UUID.randomUUID(), request.getName(), price, menuGroup, request.isDisplayed(), menuProducts, request.getMenuGroupId());
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        final BigDecimal price = request.getPrice();
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts().getMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.changePrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts().getMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.displayed();
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
