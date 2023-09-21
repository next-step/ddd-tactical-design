package kitchenpos.menus.tobe.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TobeMenuCreateRequest {
    private final String name;
    private final BigDecimal price;
    private final ToBeMenuGroupRequest menuGroup;
    private final boolean displayed;
    private final List<TobeMenuProductRequest> tobeMenuProducts;
    private final UUID menuGroupId;

    public TobeMenuCreateRequest(String name, BigDecimal price, ToBeMenuGroupRequest menuGroup, boolean displayed,
                                 List<TobeMenuProductRequest> tobeMenuProducts, UUID menuGroupId) {
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.tobeMenuProducts = tobeMenuProducts;
        this.menuGroupId = menuGroupId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ToBeMenuGroupRequest getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<TobeMenuProductRequest> getTobeMenuProducts() {
        return tobeMenuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
