package kitchenpos.menus.application.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {
    @NotNull
    private final String name;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final UUID menuGroupId;
    @NotNull
    private final boolean displayed;
    @NotEmpty
    private final List<MenuCreateProductRequest> menuProducts;

    public MenuCreateRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuCreateProductRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuCreateProductRequest> getMenuProducts() {
        return menuProducts;
    }
}
