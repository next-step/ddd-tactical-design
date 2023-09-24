package kitchenpos.menus.ui.dto.response;

import kitchenpos.menus.tobe.domain.menu.MenuId;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MenuRestResponse {

    final private UUID id;

    final private String name;

    final private BigDecimal price;

    final private UUID menuGroupId;

    final private boolean displayed;

    final private List<MenuProductRestResponse> menuProducts;

    public MenuRestResponse(UUID id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductRestResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public MenuRestResponse(MenuId id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductRestResponse> menuProducts) {
        this.id = id.getValue();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }


    public UUID getId() {
        return id;
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

    public List<MenuProductRestResponse> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }

}
