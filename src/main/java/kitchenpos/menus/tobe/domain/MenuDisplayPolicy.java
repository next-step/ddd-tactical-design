package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MenuDisplayPolicy {
    public void decideDisplayed(Menu menu, List<Product> products) {
        BigDecimal productsPrice = calculateTotalPrice(products);
        if (menu.getPrice().compareTo(productsPrice) >= 0) {
            menu.hide();
        }
    }

    private BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
