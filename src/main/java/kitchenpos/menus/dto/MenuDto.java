package kitchenpos.menus.dto;

import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;

import java.util.List;
import java.util.UUID;

public class MenuDto {

    private UUID id;
    private Name name;
    private Price price;
    private MenuGroup menuGroup;
    private boolean displayed;
    private List<MenuProduct> menuProducts;
    private UUID menuGroupId;

    public MenuDto() {
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public static MenuDto from(Menu menu) {
        final MenuDto menuDto = new MenuDto();
        menuDto.id = menu.getId();
        menuDto.name = menu.getName();
        menuDto.price = menu.getPrice();
        menuDto.menuGroup = menu.getMenuGroup();
        menuDto.displayed = menu.isDisplayed();
        menuDto.menuProducts = menu.getMenuProducts();
        menuDto.menuGroupId = menu.getMenuGroupId();

        return menuDto;
    }

}
