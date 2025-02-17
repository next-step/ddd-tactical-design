package kitchenpos.menu.domain.model;

import kitchenpos.product.domain.model.Product;

import java.util.UUID;

public class MenuProduct {
    private Long seq;
    private Product product;
    private long quantity;
    private UUID productId;

    public MenuProduct() {
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }
}
