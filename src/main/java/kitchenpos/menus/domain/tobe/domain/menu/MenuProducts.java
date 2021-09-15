package kitchenpos.menus.domain.tobe.domain.menu;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.common.domain.Price;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(final List<MenuProduct> menuProducts) {
        menuProductsShouldNotBeEmpty(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void menuProductsShouldNotBeEmpty(final List<MenuProduct> menuProducts) {
        if (menuProducts.isEmpty()) {
            throw new IllegalArgumentException("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있습니다.");
        }
    }

    public Price getAmount() {
        return menuProducts.stream()
            .map(MenuProduct::getAmount)
            .reduce(Price.ZERO, Price::add);
    }
}
