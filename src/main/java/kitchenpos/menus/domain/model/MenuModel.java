package kitchenpos.menus.domain.model;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.exception.InvalidMenuProductsPriceException;
import kitchenpos.menus.domain.vo.MenuName;
import kitchenpos.menus.domain.vo.MenuPrice;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.support.ValueObject;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuModel extends ValueObject {

    private final UUID id;
    private final MenuName menuName;
    private MenuPrice price;
    private final MenuGroupModel menuGroupModel;
    private boolean displayed;
    private final MenuProducts menuProducts;


    public MenuModel(UUID id, MenuName menuName, MenuPrice price, MenuGroupModel menuGroupModel, boolean displayed, MenuProducts menuProducts) {
        if (price.getPrice().compareTo(menuProducts.totalAmount()) > 0) {
            throw new InvalidMenuProductsPriceException();
        }
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.menuGroupModel = menuGroupModel;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public MenuModel(Menu menu, PurgomalumClient purgomalumClient) {
        this(menu.getId(),
                new MenuName(menu.getName(), purgomalumClient),
                new MenuPrice(menu.getPrice()),
                new MenuGroupModel(menu.getMenuGroup()),
                menu.isDisplayed(),
                new MenuProducts(menu.getMenuProducts().stream()
                        .map(MenuProductModel::new)
                        .collect(Collectors.toList())));
    }

    public MenuModel changePrice(BigDecimal price) {
        if (price.compareTo(menuProducts.totalAmount()) > 0) {
            throw new InvalidMenuProductsPriceException();
        }
        this.price = new MenuPrice(price);
        return this;
    }

    public MenuModel displayed() {
        this.displayed = true;
        return this;
    }

    public MenuModel hide() {
        this.displayed = false;
        return this;
    }

    public Menu toMenu() {
        return new Menu(id,
                menuName.getName(),
                price.getPrice(),
                menuGroupModel.toMenuGroup(),
                displayed,
                menuProducts.toMenuProducts(),
                menuGroupModel.toMenuGroup().getId());
    }
}
