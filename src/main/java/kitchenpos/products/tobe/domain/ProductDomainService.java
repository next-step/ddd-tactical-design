package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductDomainService {

    public Product changePrice(Product product, BigDecimal price, List<Menu> menus) {
        boolean isChanged = product.changePrice(ProductPrice.update(price));
        if (isChanged) {
            checkMenuCouldBeDisplayedAfterProductPriceChanged(menus);
        }
        return product;
    }

    private void checkMenuCouldBeDisplayedAfterProductPriceChanged(List<Menu> menus) {
        menus.forEach(menu -> {
            BigDecimal totalMenuProductPrice = menu.getMenuProducts()
                    .parallelStream()
                    .map(menuProduct -> menuProduct.getProduct().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (isMenuPriceGreaterThanSumOfMenuProducts(menu, totalMenuProductPrice)) {
                menu.setDisplayed(false);
            }
        });
    }

    private boolean isMenuPriceGreaterThanSumOfMenuProducts(Menu menu, BigDecimal totalMenuProductPrice) {
        return menu.getPrice().compareTo(totalMenuProductPrice) > 0;
    }

}
