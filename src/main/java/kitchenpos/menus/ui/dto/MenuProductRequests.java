package kitchenpos.menus.ui.dto;

import java.util.List;
import java.util.Objects;

public class MenuProductRequests {

    List<MenuProductRequest> menuProductRequests;

    public MenuProductRequests(List<MenuProductRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException("메뉴에 등록할 상품은 필수입니다.");
        }

        this.menuProductRequests = menuProductRequests;
    }

    public List<MenuProductRequest> getMenuProductRequests() {
        return menuProductRequests;
    }

    public int size() {
        return menuProductRequests.size();
    }
}
