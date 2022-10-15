package kitchenpos.menus.tobe.dto.menu;

import java.util.List;

public class MenuCreateRequest {

    private List<MenuProductRequest> menuProducts;

    public MenuCreateRequest(List<MenuProductRequest> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return this.menuProducts;
    }

}
