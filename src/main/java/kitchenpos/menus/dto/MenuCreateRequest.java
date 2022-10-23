package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {
    private UUID menuGroupId;
    private List<MenuProductRequest> menuProductRequestList;
    private BigDecimal price;
    private String name;
    private boolean displayed;

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRequest> getMenuProductRequestList() {
        return menuProductRequestList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public void setMenuProductRequestList(List<MenuProductRequest> menuProductRequestList) {
        this.menuProductRequestList = menuProductRequestList;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}
