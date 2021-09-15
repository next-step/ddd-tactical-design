package kitchenpos.menus.tobe.dto;

import kitchenpos.menuproducts.dto.MenuProductResponse;
import kitchenpos.menus.tobe.domain.Menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuResponse {
    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final MenuGroupResponse menuGroup;
    private final boolean displayed;
    private final List<MenuProductResponse> menuProducts;
    private final UUID menuGroupId;

    private MenuResponse(final UUID id, final String name, final BigDecimal price, final MenuGroupResponse menuGroup, final boolean displayed, final List<MenuProductResponse> menuProducts, final UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
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

    public MenuGroupResponse getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public static MenuResponse from(final Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                MenuGroupResponse.from(menu.getMenuGroup()),
                menu.isDisplayed(),
                menu.getMenuProducts().stream()
                        .map(MenuProductResponse::from)
                        .collect(Collectors.toList()),
                menu.getMenuGroupId()
        );
    }
}
