package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {
    private final UUID menuGroupId;
    private final BigDecimal price;
    private String menuName;
    private List<MenuProductRequest> menuProducts;

    public MenuCreateRequest(UUID menuGroupId, String menuName, BigDecimal price, List<MenuProductRequest> menuProducts) {
        this.menuGroupId = menuGroupId;
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

    public UUID getMenuGroupId() {
        return this.menuGroupId;
    }
}
