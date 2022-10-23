package kitchenpos.menus.mapper;

import kitchenpos.menus.dto.MenuProductResponse;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuProductMapper {
    public MenuProductResponse toMenuProductResponse(MenuProduct menuProduct) {
        if (menuProduct == null) {
            return new MenuProductResponse();
        }

        return new MenuProductResponse(
                menuProduct.getSeq(),
                menuProduct.getProduct().getId().getId(),
                menuProduct.getQuantity(),
                menuProduct.getProduct().getPrice(),
                menuProduct.getProduct().getName()
        );
    }

    public List<MenuProductResponse> toMenuProductResponseList(List<MenuProduct> menuProductList) {
        if (menuProductList == null || menuProductList.isEmpty()) {
            return new ArrayList<>();
        }

        return menuProductList.stream().map(
                v -> toMenuProductResponse(v)
        ).collect(Collectors.toList());
    }
}
