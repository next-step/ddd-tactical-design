package kitchenpos.menus.ui.response;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.ui.response.ProductResponse;

public class MenuProductResponse {

    private ProductResponse productResponse;
    private Long quantity;

    public MenuProductResponse(ProductResponse productResponse, Long quantity) {
        this.productResponse = productResponse;
        this.quantity = quantity;
    }

    public static MenuProductResponse of(MenuProduct menuProduct) {
        return new MenuProductResponse(
                ProductResponse.of(menuProduct.getProduct()),
                menuProduct.getQuantity()
        );
    }

}
