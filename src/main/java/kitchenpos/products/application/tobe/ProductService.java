package kitchenpos.products.application.tobe;

import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {
    private final Product product;
    private final Menu menu;

    public ProductService(Product product, Menu menu) {
        this.product = product;
        this.menu = menu;
    }

    public Product changePrice(final BigDecimal price) {
        product.changePrice(price);
        menu.checkHideMenu(product.getId(), price);

        return product;
    }
}
