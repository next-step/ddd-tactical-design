package kitchenpos.menus.tobe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateDto {
    private String name;
    private BigDecimal price;
    private boolean displayed;
    private UUID menuGroupId;
    private List<MenuProductCreateDto> menuProducts;

    @JsonCreator
    public MenuCreateDto(String name, BigDecimal price, boolean displayed, UUID menuGroupId, List<MenuProductCreateDto> menuProducts) {
        this.name = name;
        this.price = price;
        this.displayed = displayed;
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
        return displayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductCreateDto> getMenuProducts() {
        return menuProducts;
    }
}
