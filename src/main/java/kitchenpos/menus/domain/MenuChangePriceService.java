package kitchenpos.menus.domain;

import kitchenpos.menus.domain.exception.InvalidMenuProductsPriceException;
import kitchenpos.menus.domain.exception.InvalidMenuProductsSizeException;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.vo.Products;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MenuChangePriceService {
    private final ProductService productService;

    public MenuChangePriceService(ProductService productService) {
        this.productService = productService;
    }

    public Menu changePrice(Menu menu, BigDecimal price) {
        valid(menu.getMenuProducts(), price);
        return menu.changePrice(price);
    }
    private void valid(MenuProducts menuProducts, BigDecimal price) {
        validMenuProductsPrice(menuProducts, price);
        validMenuProductsSize(menuProducts);
    }

    private void validMenuProductsSize(MenuProducts menuProducts) {
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
}
