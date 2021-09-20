package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.domain.TobeMenuProduct;

import java.util.UUID;

public class MenuProductForm {
    private Long seq;
    private ProductForm product;
    private UUID productId;
    private long quantity;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public ProductForm getProductForm() {
        return product;
    }

    public void setProductForm(ProductForm product) {
        this.product = product;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public static MenuProductForm of(TobeMenuProduct menuProduct) {
        MenuProductForm menuProductForm = new MenuProductForm();
        menuProductForm.setSeq(menuProduct.getSeq());
        menuProductForm.setProductForm(ProductForm.of(menuProduct.getProduct()));
        menuProductForm.setQuantity(menuProduct.getQuantity());
        return menuProductForm;
    }
}
