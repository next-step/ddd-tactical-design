package kitchenpos.menus.application.dto;

import java.util.List;
import java.util.UUID;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.domain.tobe.DisplayedMenu;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuName;
import kitchenpos.menus.domain.tobe.MenuPrice;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.domain.tobe.Product;

public class MenuCreateRequest {

    private final MenuName name;

    private final MenuPrice price;

    private final UUID menuGroupId;

    private final DisplayedMenu displayed;

    private final MenuProductCreateRequests menuProductCreateRequests;

    public MenuCreateRequest(MenuName name, MenuPrice price, UUID menuGroupId,
            DisplayedMenu displayed,
            MenuProductCreateRequests menuProductCreateRequests) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductCreateRequests = menuProductCreateRequests;
    }

    public void validateName(ProfanityValidator profanityValidator) {
        profanityValidator.validate(name.getName());
    }

    public Menu to(MenuGroup menuGroup, List<Product> products) {
        List<MenuProduct> menuProducts = menuProductCreateRequests.toMenuProducts(products);
        return new Menu(name, price, menuGroup, displayed,
                new MenuProducts(menuProducts, products));
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<UUID> getProductIds() {
        return menuProductCreateRequests.getProductIds();
    }

    public MenuPrice getPrice() {
        return price;
    }
}
