package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.common.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.menu.domain.Menu;
import kitchenpos.menus.tobe.domain.menu.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MenuService {
    private final MenuRepository menuRepository;

    private final MenuGroupService menuGroupService;

    private final ProductService productService;

    public MenuService(final MenuRepository menuRepository, MenuGroupService menuGroupService, ProductService productService) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productService = productService;
    }

    @Transactional
    public Menu create(final Menu menu) {
        if (!menuGroupService.existsMenuGroup(menu.getMenuGroupId())) {
            throw new IllegalArgumentException();
        }

        final Menu savedMenu = menuRepository.save(menu);
        List<MenuProduct> menuProducts = menu.getMenuProducts();

        MenuProducts prices = productService.findAllPrices(menu.getMenuProductIds());

        BigDecimal totalPrice = menuProducts.stream()
                .map(menuProduct -> {
                    Price price = prices.getMenuProducts().stream()
                            .filter(productPrice -> productPrice.getProductId().equals(menuProduct.getProductId()))
                            .findAny()
                            .get()
                            .getPrice();
                    return menuProduct.multiply(price);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        savedMenu.validateMenuPrice(totalPrice);
        return savedMenu;
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
