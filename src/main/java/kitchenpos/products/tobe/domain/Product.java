package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.menus.tobe.application.ProductPriceChangeEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "TobeProduct")
@Table(name = "product")
public class Product extends AbstractAggregateRoot<Product> {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private ProductName productName;

    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice productPrice;

    protected Product() {
    }

    private Product(UUID id, ProductName name, ProductPrice productPrice) {
        this.id = id;
        this.productName = name;
        this.productPrice = productPrice;
    }

    public static Product create(ProductName name, ProductPrice price) {
        return new Product(UUID.randomUUID(), name, price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return productName.name();
    }

    public BigDecimal getPrice() {
        return productPrice.price();
    }


    public void changePrice(BigDecimal price) {
        this.productPrice = ProductPrice.of(price);
        this.registerEvent(new ProductPriceChangeEvent(this.id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id)
                && Objects.equals(productName, product.productName)
                && Objects.equals(productPrice.price(), product.productPrice.price());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productPrice);
    }

}
