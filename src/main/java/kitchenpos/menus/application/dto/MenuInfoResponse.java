package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuInfoResponse {
    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final UUID menuGroupId;
    private boolean displayed;
    private final List<MenuProductInfoResponse> menuProductInfoResponseList;

    public MenuInfoResponse(UUID id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductInfoResponse> menuProductInfoResponseList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductInfoResponseList = menuProductInfoResponseList;
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

    public List<MenuProductInfoResponse> getMenuProductCreateResponseList() {
        return menuProductInfoResponseList;
    }
}
