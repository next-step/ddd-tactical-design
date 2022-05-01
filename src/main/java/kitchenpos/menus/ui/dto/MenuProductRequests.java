package kitchenpos.menus.ui.dto;

import kitchenpos.menus.tobe.domain.MenuProductDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MenuProductRequests {

    List<MenuProductRequest> menuProductRequests;

    public MenuProductRequests(List<MenuProductRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException("메뉴에 등록할 상품은 필수입니다.");
        }

        this.menuProductRequests = menuProductRequests;
    }

    public List<MenuProductDto> getMenuProductRequests() {
        return menuProductRequests.stream()
                .map(menuProductRequest -> new MenuProductDto(menuProductRequest.getProductId(), menuProductRequest.getQuantity()))
                .collect(Collectors.toList());
    }

    public int size() {
        return menuProductRequests.size();
    }
}
