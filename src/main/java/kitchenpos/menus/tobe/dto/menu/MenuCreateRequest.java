package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;
import java.util.List;

public class MenuCreateRequest {

    private final BigDecimal price;
    private String menuName;
    private List<MenuProductRequest> menuProducts;
    private BigDecimal quantity;

    public MenuCreateRequest(String menuName, BigDecimal price, List<MenuProductRequest> menuProducts) {
        this.menuName = menuName;
        this.price = price;
        this.menuProducts = menuProducts;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return this.menuProducts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }
}
