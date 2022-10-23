package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuResponse {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean displayed;
    private MenuGroupResponse menuGroupResponse;
    private List<MenuProductResponse> menuProductResponseList;

    public MenuResponse() {}

    public MenuResponse(
            UUID id,
            String name,
            BigDecimal price,
            boolean displayed,
            MenuGroupResponse menuGroupResponse,
            List<MenuProductResponse> menuProductResponseList
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuGroupResponse = menuGroupResponse;
        this.menuProductResponseList = menuProductResponseList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public MenuGroupResponse getMenuGroupResponse() {
        return menuGroupResponse;
    }

    public void setMenuGroupResponse(MenuGroupResponse menuGroupResponse) {
        this.menuGroupResponse = menuGroupResponse;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProductResponse> getMenuProductResponseList() {
        return menuProductResponseList;
    }

    public void setMenuProductResponseList(List<MenuProductResponse> menuProductResponseList) {
        this.menuProductResponseList = menuProductResponseList;
    }
}
