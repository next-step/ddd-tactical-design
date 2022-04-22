package kitchenpos.products.dto;

import kitchenpos.products.domain.tobe.domain.TobeProduct;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import java.math.BigDecimal;

public class ProductRegisterResponse {
    private ProductId productId;
    private String productName;
    private BigDecimal price;

    public ProductRegisterResponse() {
    }

    public ProductRegisterResponse(ProductId productId, String productName, BigDecimal price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public ProductRegisterResponse(TobeProduct product) {
        this.productId = product.getId();
        this.productName = product.getName().getValue();
        this.price = product.getPrice().getValue();
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductRegisterResponse that = (ProductRegisterResponse) o;

        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
