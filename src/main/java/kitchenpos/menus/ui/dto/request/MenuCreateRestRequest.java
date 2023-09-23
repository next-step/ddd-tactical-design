package kitchenpos.menus.ui.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRestRequest {

    final private BigDecimal price;
    final private UUID menuGroupId;
    final private List<MenuProductRestRequest> menuProducts;
    final private String name;
    final private boolean displayed;

    public MenuCreateRestRequest(BigDecimal price, UUID menuGroupId, List<MenuProductRestRequest> menuProducts, String name, boolean displayed) {
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.name = name;
        this.displayed = displayed;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRestRequest> getMenuProducts() {
        return menuProducts;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

}
