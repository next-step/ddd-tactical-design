package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id", nullable = false)

    private List<MenuProduct> values = new ArrayList<>();

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> values) {
        if (values.isEmpty()) {
            throw new MenuProductException(MenuErrorCode.MENU_PRODUCT_IS_EMPTY);
        }
        this.values = values;
    }

    public BigDecimal calculateSum() {
        return values.stream()
                .map(MenuProduct::calculatePrice)
                .reduce(Price::add)
                .get()
                .getValue();
    }

    public List<MenuProduct> getValues() {
        return Collections.unmodifiableList(values);
    }

    public List<ProductId> getProductIds() {
        return values.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toUnmodifiableList());
    }

    public void fetchPrice(ProductId productId, Price productPrice) {
        this.values
                .stream()
                .filter(menuProduct -> menuProduct.hasProduct(productId))
                .forEach(menuProduct -> menuProduct.fetchPrice(productPrice));
    }
}
