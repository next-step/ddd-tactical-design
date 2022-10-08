package kitchenpos.menus.ui.response;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;

public class MenuResponse {

    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final UUID menuGroupId;
    private final boolean displayed;
    private final List<MenuProductResponse> menuProducts;

    public MenuResponse(
        UUID id,
        String name,
        BigDecimal price,
        UUID menuGroupId,
        boolean displayed,
        List<MenuProductResponse> menuProducts
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
            menu.getId(),
            menu.getNameValue(),
            menu.getPriceValue(),
            menu.getMenuGroupId(),
            menu.isDisplayed(),
            MenuProductResponse.of(menu.getMenuProductValues())
        );
    }

    public static List<MenuResponse> of(List<Menu> menus) {
        return menus.stream()
            .map(MenuResponse::from)
            .collect(toList());
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
        return menuProducts;
    }
}
