package kitchenpos.menus.ui.response;

import kitchenpos.menus.tobe.domain.MenuProduct;

public class MenuProductResponse {

    private ProductResponse productResponse;
    private Long quantity;

    public MenuProductResponse(ProductResponse productResponse, Long quantity) {
        this.productResponse = productResponse;
        this.quantity = quantity;
    }

    public static MenuProductResponse of(MenuProduct menuProduct) {
        return new MenuProductResponse(
                new ProductResponse(menuProduct.getId()),
                menuProduct.getQuantity()
        );
    }

}
