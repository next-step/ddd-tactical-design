package kitchenpos.menus.application.dto;

import java.util.List;
import java.util.UUID;

public class CreateMenuRequest {
    private String name;
    private Long price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductDto> menuProducts;

    public CreateMenuRequest() {
    }

    public CreateMenuRequest(String name, Long price, UUID menuGroupId, boolean displayed, List<MenuProductDto> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }
}
