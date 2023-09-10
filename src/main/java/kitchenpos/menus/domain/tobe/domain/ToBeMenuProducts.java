package kitchenpos.menus.domain.tobe.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import kitchenpos.products.domain.tobe.domain.Price;

@Embeddable
public class ToBeMenuProducts {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
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

    public Price getSumOfProducts() {
        return value.stream()
            .map(it -> it.getProductPrice())
            .reduce(Price.of(0), Price::add);
    }
}
