package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.vo.Price;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {

    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (CollectionUtils.isEmpty(menuProducts)) {
            throw new IllegalArgumentException("메뉴 상품을 확인 하세요.");
        }
        this.menuProducts = menuProducts;
    }

    public int getMenuProductsCount() {
        return menuProducts.size();
    }

    public BigDecimal getMenuProductsTotalPrice() {
        return menuProducts.stream()
                .map(MenuProduct::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void changePrice(UUID productId, Price price) {
        menuProducts.stream()
                .filter(menuProduct -> menuProduct.getProductId().equals(productId))
                .forEach(menuProduct -> menuProduct.changePrice(price));
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
