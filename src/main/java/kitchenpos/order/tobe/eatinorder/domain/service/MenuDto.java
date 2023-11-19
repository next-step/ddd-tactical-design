package kitchenpos.order.tobe.eatinorder.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.Menu;

public class MenuDto {

    private UUID id;

    private String name;

    private BigDecimal price;

    private UUID menuGroupId;

    private boolean displayed;

    private List<MenuProductDto> menuProducts;

    public MenuDto(UUID id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductDto> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static MenuDto from(Menu menu) {
        var menuProducts = menu.getMenuProducts().getValue()
            .stream()
            .map(MenuProductDto::from)
            .collect(Collectors.toList());

        return new MenuDto(menu.getId(), menu.getNameValue(), menu.getPrice(), menu.getMenuGroupId(), menu.isDisplayed(),
            menuProducts);
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

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }

    public boolean isHide() {
        return !this.displayed;
    }
}
