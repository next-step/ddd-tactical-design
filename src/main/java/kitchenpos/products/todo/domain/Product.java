package kitchenpos.products.todo.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Embedded
    @Column(name = "price")
    private ProductPrice price;

    public Product() {
    }

    public Product(String name, ProductPrice price) {
        this(null, name, price);
    }

    public Product(Long id, String name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(ProductRequest request) {
        this.name = request.getName();
        this.price = new ProductPrice(request.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
    }

    public void setPrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
