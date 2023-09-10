package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.products.tobe.domain.ProductPrice;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MenuProduct> values;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> values) {
        if (values.isEmpty()) {
            throw new MenuProductException(MenuErrorCode.MENU_PRODUCT_IS_EMPTY);
        }
        this.values = values;
    }

    public List<MenuProduct> getValues() {
        return Collections.unmodifiableList(values);
    }

    public BigDecimal getSum() {
        return values.stream()
                .map(MenuProduct::getProductPrice)
                .reduce(ProductPrice::add)
                .get()
                .getValue();
    }

    public void setMenu(Menu menu) {
        values.forEach(menuProduct -> menuProduct.setMenu(menu));
    }
}
