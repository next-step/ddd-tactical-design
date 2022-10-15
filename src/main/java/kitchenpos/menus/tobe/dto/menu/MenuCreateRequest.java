package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;
import java.util.List;

public class MenuCreateRequest {

    private final BigDecimal price;
    private List<MenuProductRequest> menuProducts;

    public MenuCreateRequest(BigDecimal price, List<MenuProductRequest> menuProducts) {
        this.price = price;
        this.menuProducts = menuProducts;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return this.menuProducts;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
