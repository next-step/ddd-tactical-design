package kitchenpos.menus.ui.response;

import kitchenpos.menus.tobe.domain.Menu;

import java.math.BigDecimal;
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

    public MenuResponse(UUID id, String name, BigDecimal price,
                        MenuGroupResponse menuGroup, boolean displayed,
                        List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse of(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                MenuGroupResponse.of(menu.getMenuGroup()),
                menu.isDisplayed(),
                menu.getMenuProducts().stream().map(MenuProductResponse::of).collect(Collectors.toList())
        );
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
}
