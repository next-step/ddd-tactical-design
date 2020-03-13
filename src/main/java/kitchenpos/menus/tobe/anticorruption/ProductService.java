package kitchenpos.menus.tobe.anticorruption;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * MenuProduct의 금액의 합은 Menu의 가격보다 크거나 같다.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void calculateMenuPrice(Menu menu) {
        List<MenuProduct> menuProducts = menu.getMenuProducts().list();

        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            Long productId = menuProduct.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(IllegalArgumentException::new);
            sum.add(product.getPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())));
        }

        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }

        menu.changeMenuProducts(new MenuProducts(sum, menuProducts));
    }
}
