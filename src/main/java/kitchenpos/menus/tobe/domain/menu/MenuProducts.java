package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.global.vo.Price;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Embeddable
public class MenuProducts {

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {

    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        if (CollectionUtils.isEmpty(menuProducts)) {
            throw new IllegalArgumentException("메뉴 상품은 비어있을 수 없습니다.");
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
