package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.dto.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MenuProductResponse {

    private Long seq;
    private ProductResponse product;
    private long quantity;

    public MenuProductResponse(Long seq, ProductResponse product, long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = quantity;
    }

    public static MenuProductResponse fromEntity(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getSeq(),
                ProductResponse.fromEntity(menuProduct.getProduct()),
                menuProduct.getQuantityValue()
        );
    }

    public static List<MenuProductResponse> fromEntities(MenuProducts menuProducts) {
        return menuProducts.getValues()
                .stream()
                .map(MenuProductResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }
}
