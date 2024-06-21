package kitchenpos.menus.ui.dto;

import java.util.UUID;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.domain.tobe.DisplayedMenu;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuName;
import kitchenpos.menus.domain.tobe.MenuPrice;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.products.domain.ProfanityValidator;

public class MenuCreateRequest {

    private MenuName name;

    private MenuPrice price;

    private UUID menuGroupId;

    private DisplayedMenu displayed;

    private MenuProductCreateRequests menuProducts;

    public MenuCreateRequest(MenuName name, MenuPrice price, UUID menuGroupId,
            DisplayedMenu displayed,
            MenuProductCreateRequests menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void validateName(ProfanityValidator profanityValidator) {
        profanityValidator.validate(name.getName());
    }

    public Menu to(MenuGroup menuGroup, MenuProducts menuProducts) {
        return new Menu(name, price, menuGroup, displayed, menuProducts);
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public MenuProductCreateRequests getMenuProducts() {
        return menuProducts;
    }

    public MenuPrice getPrice() {
        return price;
    }
}
