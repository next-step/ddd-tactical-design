package kitchenpos.menus.ui.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {

    private String name;
    private BigDecimal price;
    private UUID menuGroupId;
    private List<MenuProductCreateRequest> menuProductCreateRequests;

    protected MenuCreateRequest() {
    }

    public MenuCreateRequest(
        String name,
        BigDecimal price,
        UUID menuGroupId,
        List<MenuProductCreateRequest> menuProductCreateRequests
    ) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProductCreateRequests = menuProductCreateRequests;
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

    public List<MenuProductCreateRequest> getMenuProductCreateRequests() {
        return menuProductCreateRequests;
    }
}
