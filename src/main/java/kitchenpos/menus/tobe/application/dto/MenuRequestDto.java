package kitchenpos.menus.tobe.application.dto;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menus.tobe.domain.MenuProduct;

public class MenuRequestDto {

    private String name;
    private BigDecimal price;
    private Long menuGroupId;
    private List<MenuProduct> menuProducts;

    public MenuRequestDto(String name, BigDecimal price, Long menuGroupId,
        List<MenuProduct> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
