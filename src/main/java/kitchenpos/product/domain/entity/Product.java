package kitchenpos.product.domain.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import kitchenpos.product.domain.model.ProductId;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "product")
@Entity
@DynamicUpdate
public class Product {

    @EmbeddedId
    private ProductId productId;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product() {}

    public Product(ProductId productId, ProductName name, ProductPrice price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice(long qty) {
        return price.multiply(qty).get();
    }

    public void updatePrice(ProductPrice price) {
        this.price = price;
    }

}
