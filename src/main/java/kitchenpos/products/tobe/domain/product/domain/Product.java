package kitchenpos.products.tobe.domain.product.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
@Access(AccessType.FIELD)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductInfo productInfo;

    private Product() {
    }

    public Product(Long id, String name, BigDecimal price) {
        this.id = id;
        this.productInfo = new ProductInfo(name, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return productInfo.getName();
    }

    public BigDecimal getPrice() {
        return productInfo.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(productInfo, product.productInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productInfo);
    }
}
