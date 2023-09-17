package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuId;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuResponse {

    private UUID id;

    private String name;

    private BigDecimal price;

    private UUID menuGroupId;

    private boolean displayed;

    private List<MenuProductResponse> menuProducts;

    public MenuResponse() {
    }

    public MenuResponse(MenuId id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductResponse> menuProducts) {
        this.id = id.getValue();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse fromEntity(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getNameValue(),
                menu.getPriceValue(),
                menu.getMenuGroupIdValue(),
                menu.isDisplayed(),
                MenuProductResponse.fromEntities(menu.getMenuProducts())
        );
    }

    public static List<MenuResponse> fromEntities(List<Menu> menus) {
        return menus.stream()
                .map(MenuResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
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

    public List<MenuProductResponse> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }

}
