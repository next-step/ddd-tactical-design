package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {

    private List<MenuProduct> menuProducts;

    protected MenuProducts() {

    }

    public MenuProducts(MenuProduct... menuProducts) {
        if (menuProducts.length <= 0) {
            throw new IllegalArgumentException();
        }

        this.menuProducts = Arrays.stream(menuProducts)
                .collect(Collectors.toList());
    }

    public BigDecimal getPriceSum() {
        List<BigDecimal> amounts = this.menuProducts.stream()
                .map(MenuProduct::getAmount)
                .collect(Collectors.toList());

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal amount : amounts) {
            sum = sum.add(amount);
        }
        return sum;
    }

}
