package kitchenpos.menus.domain.tobe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MenuProducts {
    @ElementCollection
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(final List<MenuProduct> menuProducts) {
        if (Optional.ofNullable(menuProducts).isEmpty() || menuProducts.size() < 1) {
            throw new IllegalArgumentException("메뉴상품 1개 이상 필요합니다.");
        }

        this.menuProducts = menuProducts;
    }

    public BigDecimal totalAmount() {
        return menuProducts.stream()
                .map(MenuProduct::amount)
                .reduce(BigDecimal::add).get();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void changeMenuProductsPrice(final UUID productId, final BigDecimal price) {
        this.menuProducts.stream()
                .filter(a -> productId.equals(a.getProductId()))
                .forEach(a -> a.changePrice(price));
    }

    public boolean isExpensiveTotalPrice(final Price price) {
        if (price.comparePrice(this.totalAmount()) >= 1) {
            return true;
        }

        return false;
    }

}
