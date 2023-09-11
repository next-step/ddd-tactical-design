package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.Menu;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuResponse {

    private UUID id;

    private String name;

    private BigDecimal price;

    private MenuGroupResponse menuGroup;

    private boolean displayed;

    private List<MenuProductResponse> menuProducts;

    public MenuResponse() {
    }

    public MenuResponse(UUID id, String name, BigDecimal price, MenuGroupResponse menuGroup, boolean displayed, List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse fromEntity(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getNameValue(),
                menu.getPriceValue(),
                MenuGroupResponse.fromEntity(menu.getMenuGroup()),
                menu.isDisplayed(),
                MenuProductResponse.fromEntities(menu.getMenuProducts())
        );
    }

    public static List<MenuResponse> fromEntities(List<Menu> menus){
        return menus.stream()
                .map(MenuResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
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

    public MenuGroupResponse getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuGroupResponse menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }

    public void setMenuProducts(List<MenuProductResponse> menuProducts) {
        this.menuProducts = menuProducts;
    }
}
