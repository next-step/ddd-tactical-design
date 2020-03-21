package kitchenpos.products.todo.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Embedded
    @Column(name = "price", nullable = false)
    private ProductPrice price;

    public Product() {
    }

    public Product(Long id, String name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(String name, ProductPrice price) {
        this(null, name, price);
    }

    public Product(String name, BigDecimal price) {
        this(name, new ProductPrice(price));
    }

    public Product(ProductRequest request) {
        this(request.getName(), request.getPrice());
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

    public void setPrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }
}
