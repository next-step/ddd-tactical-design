package kitchenpos.menus.tobe.domain.model;

import kitchenpos.menus.tobe.domain.validator.MenuValidator;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {

    private List<MenuProduct> menuProducts;

    protected MenuProducts() {

    }

    public MenuProducts(List<MenuProduct> menuProducts, MenuValidator menuValidator) {
        menuValidator.validateMenuProducts(menuProducts);
        this.menuProducts = menuProducts;
    }

    public BigDecimal calculateSum() {
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
