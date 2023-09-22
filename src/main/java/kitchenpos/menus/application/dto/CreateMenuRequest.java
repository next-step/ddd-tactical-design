package kitchenpos.menus.application.dto;

import java.util.List;
import java.util.UUID;

public class CreateMenuRequest {
    private String name;
    private Long price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductDto> menuProducts;

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
