package kitchenpos.menus.tobe.menu.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.menu.domain.model.MenuProduct;
import kitchenpos.menus.tobe.menu.domain.model.MenuProducts;

public class MenuProductResponse {

    private Long seq;
    private ProductResponse product;
    private Long quantity;
    private UUID productId;

    protected MenuProductResponse() {
    }

    private MenuProductResponse(final MenuProduct menuProduct) {
        this.seq = menuProduct.getSeq();
        // TODO
//        this.product = menuProduct.getProductId();
        this.quantity = menuProduct.getQuantity()
            .getValue();
        this.productId = menuProduct.getProductId();
    }

    public static MenuProductResponse from(final MenuProduct menuProduct) {
        return new MenuProductResponse(menuProduct);
    }

    public static List<MenuProductResponse> from(final MenuProducts menuProducts) {
        return menuProducts.getMenuProducts()
            .stream()
            .map(MenuProductResponse::from)
            .collect(Collectors.toList());
    }

    public Long getSeq() {
        return seq;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }
}
