package kitchenpos.menus.tobe.v2.dto;

public class MenuProductRequestDto {
    private Long productId;
    private long quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
