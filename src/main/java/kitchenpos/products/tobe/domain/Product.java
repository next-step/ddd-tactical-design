package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product {

    @EmbeddedId
    private ProductId productId;

    @Embedded
    private ProductName productName;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(ProductId productid, ProductName productName, Price price) {
        this.productId = productid;
        this.productName = productName;
        this.price = price;
    }

    public ProductId getProductId() {
        return productId;
    }

    public ProductName getProductName() {
        return productName;
    }

    public Price getPrice() {
        return price;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public void setProductName(ProductName productName) {
        this.productName = productName;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public static kitchenpos.products.domain.Product convertAsisDomain(Product product){
        kitchenpos.products.domain.Product asisProduct = new kitchenpos.products.domain.Product();
        asisProduct.setId(product.productId.getId());
        asisProduct.setName(product.productName.getValue());
        asisProduct.setPrice(product.price.getPrice());
        return asisProduct;
    }
}
