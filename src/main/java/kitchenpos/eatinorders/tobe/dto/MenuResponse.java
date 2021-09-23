package kitchenpos.eatinorders.tobe.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuResponse {
    private UUID id;
    private String name;
    private BigDecimal price;
    private MenuGroupResponse menuGroup;
    private boolean displayed;
    private List<MenuProductResponse> menuProducts;
    private UUID menuGroupId;

    protected MenuResponse() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuGroupResponse getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return new ArrayList<>(menuProducts);
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
