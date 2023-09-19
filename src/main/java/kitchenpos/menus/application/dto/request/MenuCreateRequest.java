package kitchenpos.menus.application.dto.request;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.domain.PurgomalumClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {
    private String name;
    private BigDecimal price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductCreateRequest> menuProductCreateRequests;

    public MenuCreateRequest() {
    }

    public MenuCreateRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed,
                             List<MenuProductCreateRequest> menuProductCreateRequests) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
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

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductCreateRequest> getMenuProductCreateRequests() {
        return menuProductCreateRequests;
    }

    public Menu toMenu(PurgomalumClient purgomalumClient, List<MenuProduct> menuProducts) {
        return new Menu(this.name, purgomalumClient, this.price, this.menuGroupId, this.displayed, menuProducts);
    }
}
