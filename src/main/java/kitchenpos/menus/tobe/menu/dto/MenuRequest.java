package kitchenpos.menus.tobe.menu.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MenuRequest {

    private String name;
    private BigDecimal price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductRequest> menuProducts;

    protected MenuRequest() {
    }

    public MenuRequest(final String name, final BigDecimal price, final UUID menuGroupId, final boolean displayed, final MenuProductRequest... menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = Arrays.asList(menuProducts);
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

    public List<MenuProductRequest> getMenuProducts() {
        return menuProducts;
    }

}
