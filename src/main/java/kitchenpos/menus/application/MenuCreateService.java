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

    public MenuCreateService(ProductService productService) {
        this.productService = productService;
    }

    public void validMenuProduct(Menu menu) {
        MenuProducts menuProducts = menu.getMenuProducts();

        Products products = productService.findAllByIdIn(menuProducts.getProductIds());

        if (products.size() != menuProducts.size()) {
            throw new InvalidMenuProductsSizeException();
        }
    }
}
