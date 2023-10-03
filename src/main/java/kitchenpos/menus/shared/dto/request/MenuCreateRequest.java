package kitchenpos.menus.shared.dto.request;

import kitchenpos.menus.shared.dto.MenuProductDto;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuName;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {

    private String name;
    private BigDecimal price;
    private UUID menuGroupId;
    private List<MenuProductDto> menuProducts;
    private Boolean displayed;

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }

    public MenuCreateRequest(String name, BigDecimal price, UUID menuGroupId, List<MenuProductDto> menuProducts, Boolean displayed) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    public static Menu toEntity(MenuCreateRequest request) {
        return Menu.of(null, null, null, false, null );
    }

}
