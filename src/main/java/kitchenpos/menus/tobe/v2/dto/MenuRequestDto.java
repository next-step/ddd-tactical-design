package kitchenpos.menus.tobe.v2.dto;

import kitchenpos.menus.tobe.v2.domain.Menu;

import java.math.BigDecimal;
import java.util.List;

public class MenuRequestDto {
    private String name;
    private BigDecimal price;
    private Long menuGroupId;
    private List<MenuProductRequestDto> menuProducts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(Long menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public List<MenuProductRequestDto> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProductRequestDto> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public Menu toEntity() {
        return new Menu(name, price);
    }
}
