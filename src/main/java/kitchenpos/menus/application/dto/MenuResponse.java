package kitchenpos.menus.application.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;

public class MenuResponse {
    private UUID id;
    private String name;
    private Long price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductResponse> menuProducts;

    public MenuResponse(
            final UUID id,
            final String name,
            final Long price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public MenuResponse(Menu menu) {
        this(menu.getId(),
                menu.getName().stringValue(),
                menu.getPrice().longValue(),
                menu.getMenuGroup().getId(),
                menu.isDisplayed(),
                toMenuProductResponses(menu.getMenuProducts())
        );
    }

    private static List<MenuProductResponse> toMenuProductResponses(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(MenuProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return menuProducts;
    }
}
