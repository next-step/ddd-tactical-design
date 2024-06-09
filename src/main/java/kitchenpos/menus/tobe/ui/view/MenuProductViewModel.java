package kitchenpos.menus.tobe.ui.view;

import kitchenpos.menus.tobe.application.query.result.MenuQueryResult;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;

public class MenuProductViewModel {
    private final String productId;
    private final String productName;
    private final long productPrice;
    private final long quantity;

    private MenuProductViewModel(String productId, String productName, long productPrice, long quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public static MenuProductViewModel from(MenuProduct menuProduct) {
        if (menuProduct == null) {
            return null;
        }
        return new MenuProductViewModel(
            menuProduct.getProductId().toString(),
            null,
            -1,
            menuProduct.getQuantity()
        );
    }

    public static MenuProductViewModel from(MenuQueryResult menuQueryResult) {
        if (menuQueryResult == null) {
            return null;
        }

        return new MenuProductViewModel(
            menuQueryResult.getProductId(),
            menuQueryResult.getProductName(),
            menuQueryResult.getProductPrice().longValue(),
            menuQueryResult.getProductQuantity()
        );
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantity() {
        return quantity;
    }
}
