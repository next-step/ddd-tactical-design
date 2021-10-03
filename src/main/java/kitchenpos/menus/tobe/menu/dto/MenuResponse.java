package kitchenpos.menus.tobe.menu.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.menu.domain.model.Menu;
import kitchenpos.menus.tobe.menugroup.dto.MenuGroupResponse;

public class MenuResponse {
    private UUID id;
    private String name;
    private MenuGroupResponse menuGroup;
    private boolean displayed;
    private List<MenuProductResponse> menuProducts;

    protected MenuResponse() {
    }

    private MenuResponse(final Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName().getName();
//        this.menuGroup = MenuGroupResponse.from(menuGroup);
        this.displayed = menu.isDisplayed();
        this.menuProducts = MenuProductResponse.from(menu.getMenuProducts());
    }

    public static MenuResponse from(final Menu menu) {
        return new MenuResponse(menu);
    }

    public static List<MenuResponse> from(final List<Menu> menus) {
        return menus.stream().map(menu -> MenuResponse.from(menu))
            .collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
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
}
