package kitchenpos.menus.ui.dto;

import kitchenpos.common.external.PurgomalumClient;
import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.*;

import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {

    private String name;

    private PurgomalumClient purgomalumClient;

    private long price;

    private UUID menuGroupId;

    private boolean isDisplayed;

    private List<MenuProduct> products;

    public MenuCreateRequest(String name, PurgomalumClient purgomalumClient, long price, UUID menuGroupId, boolean isDisplayed, List<MenuProduct> products) {
        this.name = name;
        this.purgomalumClient = purgomalumClient;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.isDisplayed = isDisplayed;
        this.products = products;
    }

    public static Menu From(MenuCreateRequest request) {
        return new Menu(
                new MenuName(request.getName(), request.getPurgomalumClient()),
                new Price(request.getPrice()),
                new MenuGroupId(request.getMenuGroupId()),
                new MenuProducts(request.getProducts()),
                request.isDisplayed()
        );
    }

    public String getName() {
        return name;
    }

    public PurgomalumClient getPurgomalumClient() {
        return purgomalumClient;
    }

    public long getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public List<MenuProduct> getProducts() {
        return products;
    }
}
