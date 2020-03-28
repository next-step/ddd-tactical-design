package kitchenpos.menus.tobe.menu.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class MenuProducts {
    @ElementCollection
    @CollectionTable(name = "menu_product", joinColumns = @JoinColumn(name = "menu_id"))
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(final List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("제품을 1개 이상 지정해야합니다.");
        }
        validateDuplicateProduct(menuProducts);
        this.menuProducts = new ArrayList<>(menuProducts);
    }

    private void validateDuplicateProduct(final List<MenuProduct> menuProducts) {
        if (menuProducts.size() != menuProducts.stream()
                .map(MenuProduct::getProductId)
                .distinct()
                .count()) {
            throw new IllegalArgumentException("메뉴 내 제품은 중복될 수 없습니다.");
        }
    }

    public BigDecimal getTotalPrice() {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.getPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ;
    }

    public List<MenuProduct> get() {
        return new ArrayList<>(menuProducts);
    }
}
