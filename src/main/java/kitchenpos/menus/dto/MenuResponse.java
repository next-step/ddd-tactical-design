package kitchenpos.menus.dto;

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
}
