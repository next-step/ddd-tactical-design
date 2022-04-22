package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MenuRequest {
    private String menuName;
    private BigDecimal menuPrice;
    private UUID menuGroupId;
    private List<MenuProductRequest> menuProductRequests;

    private Boolean isDisplayed;

    public MenuRequest(String menuName, BigDecimal menuPrice, UUID menuGroupId, List<MenuProductRequest> menuProductRequests, Boolean isDisplayed) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroupId = menuGroupId;
        this.menuProductRequests = menuProductRequests;
        this.isDisplayed = isDisplayed;
    }

    public String getMenuName() {
        return menuName;
    }

    public BigDecimal getMenuPrice() {
        return menuPrice;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRequest> getMenuProductRequests() {
        return Collections.unmodifiableList(menuProductRequests);
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }
}
