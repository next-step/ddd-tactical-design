package kitchenpos.menus.domain;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.domain.exception.InvalidMenuProductsPriceException;
import kitchenpos.menus.domain.exception.InvalidMenuProductsSizeException;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.vo.Products;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MenuCreateService {
    private final ProductService productService;
    private final MenuGroupService menuGroupService;
    private final PurgomalumClient purgomalumClient;

    public MenuCreateService(ProductService productService, MenuGroupService menuGroupService, PurgomalumClient purgomalumClient) {
        this.productService = productService;
        this.menuGroupService = menuGroupService;
        this.purgomalumClient = purgomalumClient;
    }

    public Menu create(Menu menu) {
        valid(menu);
        return new Menu(menu, purgomalumClient);
    }

    public void valid(Menu menu) {
        validMenuProduct(menu);
        validMenuProductsPrice(menu.getMenuProducts(), menu.getPrice());
        validMenuGroup(menu);

    }

    public void validMenuProduct(Menu menu) {
        MenuProducts menuProducts = menu.getMenuProducts();

        Products products = productService.findAllByIdIn(menuProducts.getProductIds());
        if (products.size() != menuProducts.size()) {
            throw new InvalidMenuProductsSizeException();
        }
    }

    private void validMenuProductsPrice(MenuProducts menuProducts, BigDecimal price) {
        if (price.compareTo(menuProducts.totalAmount()) > 0) {
            throw new InvalidMenuProductsPriceException();
        }
    }
    public void validMenuGroup(Menu menu) {
        menuGroupService.findById(menu.getMenuGroupId());
    }
}
