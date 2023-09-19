package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.exception.InvalidMenuProductsSizeException;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.vo.Products;
import org.springframework.stereotype.Service;

@Service
public class MenuCreateService {
    private final ProductService productService;
    public final MenuGroupService menuGroupService;

    public MenuCreateService(ProductService productService, MenuGroupService menuGroupService) {
        this.productService = productService;
        this.menuGroupService = menuGroupService;
    }

    public void valid(Menu menu) {
        validMenuProduct(menu);
        validMenuGroup(menu);
    }
    public void validMenuProduct(Menu menu) {
        MenuProducts menuProducts = menu.getMenuProducts();

        Products products = productService.findAllByIdIn(menuProducts.getProductIds());
        if (products.size() != menuProducts.size()) {
            throw new InvalidMenuProductsSizeException();
        }
    }

    public void validMenuGroup(Menu menu) {
        menuGroupService.findById(menu.getMenuGroupId());
    }
}
