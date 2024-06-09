package kitchenpos.menus.tobe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateDto {
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private List<MenuProductCreateDto> menuProducts;

    @JsonCreator
    public MenuCreateDto(String name, BigDecimal price, boolean isDisplayed, UUID menuGroupId, List<MenuProductCreateDto> menuProducts) {
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductCreateDto> getMenuProducts() {
        return menuProducts;
    }
}
