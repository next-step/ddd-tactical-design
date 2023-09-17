package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuProductCreateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {
    private BigDecimal price;
    private UUID menuGroupId;
    private List<MenuProductCreateRequest> menuProducts;
    private String name;
    private boolean displayed;

    public MenuCreateRequest() {}

    private MenuCreateRequest(BigDecimal price, UUID menuGroupId, List<MenuProductCreateRequest> menuProducts, String name, boolean displayed) {
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.name = name;
        this.displayed = displayed;
    }

    public static MenuCreateRequest create(BigDecimal price, UUID menuGroupId, List<MenuProductCreateRequest> menuProductCreateRequests, String name, boolean displayed) {
        return new MenuCreateRequest(price, menuGroupId, menuProductCreateRequests, name, displayed);
    }


    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductCreateRequest> getMenuProducts() {
        return menuProducts;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

}
