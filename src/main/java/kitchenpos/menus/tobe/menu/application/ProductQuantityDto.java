package kitchenpos.menus.tobe.menu.application;

public class ProductQuantityDto {
    private Long productId;
    private Long quantity;

    public ProductQuantityDto(final Long productId, final Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }
}
