package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.exception.InvalidMenuProductsSizeException;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.vo.Products;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuCreateService {
    private final ProductService productService;

    public MenuCreateService(ProductService productService) {
        this.productService = productService;
    }

    public void validMenuProduct(Menu menu) {
        List<MenuProduct> menuProducts = menu.getMenuProducts();
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new InvalidMenuProductsSizeException();
        }
        Products products = productService.findAllByIdIn(menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList()));

        if (products.size() != menuProducts.size()) {
            throw new InvalidMenuProductsSizeException();
        }
    }
}
