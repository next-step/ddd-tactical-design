package kitchenpos.menus.ui.dto.request;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.domain.PurgomalumClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Menu toMenu(PurgomalumClient purgomalumClient) {
        toMenuProducts();
        return new Menu(this.name, purgomalumClient, this.price, this.menuGroupId, this.displayed, toMenuProducts());
    }

    private List<MenuProduct> toMenuProducts() {
        return menuProductCreateRequests.stream()
                .map(MenuProductCreateRequest::toMenuProduct)
                .collect(Collectors.toList());
    }

}
