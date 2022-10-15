package kitchenpos.menus.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuRequest {
    private BigDecimal price;
    private UUID menuGroupId;
    private String name;
    private boolean isDisplayed;
    private List<MenuProductRequest> menuProductRequests;

    public MenuRequest(BigDecimal price, UUID menuGroupId, String name, boolean isDisplayed, List<MenuProductRequest> menuProductRequests) {
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.name = name;
        this.isDisplayed = isDisplayed;
        this.menuProductRequests = menuProductRequests;
    }

    public BigDecimal price() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID menuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    public List<MenuProductRequest> menuProductRequests() {
        return menuProductRequests;
    }

    public void setMenuProductRequests(List<MenuProductRequest> menuProductRequests) {
        this.menuProductRequests = menuProductRequests;
    }
}
