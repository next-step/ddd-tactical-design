package kitchenpos.menus.ui.dto;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuRequest {

    private String name;

    private BigDecimal price;

    private boolean displayed;

    private List<MenuProductsRequest> menuProducts;

    private UUID menuGroupId;

    public MenuRequest(BigDecimal price) {
        this.price = price;
    }

    public MenuRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductsRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductsRequest> getMenuProducts() {
        return menuProducts;
    }

    public Menu toEntity(PurgomalumClient purgomalumClient, MenuGroup menuGroup) {
        List<MenuProduct> menuProducts = this.menuProducts.stream()
                .map(menuProductsRequest -> menuProductsRequest.toEntity())
                .collect(Collectors.toList());
        return new Menu(purgomalumClient, name, price, menuGroup, displayed, menuProducts);
    }
}
