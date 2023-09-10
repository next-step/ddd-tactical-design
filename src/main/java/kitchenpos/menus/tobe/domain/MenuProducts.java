package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.products.tobe.domain.ProductPrice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {
    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )

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
}
