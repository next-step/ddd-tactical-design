package kitchenpos.menus.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class ToBeMenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_tobe_menu_product_to_menu")
    )
    private List<ToBeMenuProduct> value;

    protected ToBeMenuProducts() {
    }

    public ToBeMenuProducts(List<ToBeMenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("상품이 없으면 등록할 수 없다.");
        }
        this.value = menuProducts;
    }

    public BigDecimal sumOfProducts() {
        return value.stream()
            .map(ToBeMenuProduct::amount)
            .reduce(MenuProductPrice.ZERO, MenuProductPrice::add)
            .getValue();
    }

    public void changePrice(UUID productId, BigDecimal price) {
        value.stream()
            .filter(it -> it.isSameMenuProduct(productId))
            .forEach(it -> it.changePrice(price));
    }
}
