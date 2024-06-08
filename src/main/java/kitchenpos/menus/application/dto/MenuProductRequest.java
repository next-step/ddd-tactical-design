package kitchenpos.menus.application.dto;

import kitchenpos.menus.domain.tobe.menu.MenuProduct;
import kitchenpos.menus.domain.tobe.menu.MenuProducts;
import kitchenpos.products.domain.tobe.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductRequest {
    private Long seq;

    private Product product;

    private long quantity;

    private UUID productId;

    public MenuProductRequest() {
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

    public MenuProduct toMenuProducts(){
        return MenuProduct.of(getProductId(), getProductPrice(), getQuantity());
    }

    public BigDecimal getProductPrice(){
        return product.getProductPrice();
    }

}
