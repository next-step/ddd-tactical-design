package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;

public class MenuProductRequest {

    private final BigDecimal quantity;
    private ProductRequest productRequest;

    public MenuProductRequest(ProductRequest productRequest, BigDecimal quantity) {
        this.productRequest = productRequest;
        this.quantity = quantity;
    }

    public ProductRequest getProductRequest() {
        return productRequest;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

}
