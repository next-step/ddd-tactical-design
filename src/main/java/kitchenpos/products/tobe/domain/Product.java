package kitchenpos.products.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Embedded
    private ProductPrice price;

    protected Product() {}

    public Product(final String name, final Long price) {
        if(Strings.isBlank(name)) {
            throw new IllegalArgumentException("제품명은 빈 문자열일 수 없습니다.");
        }

        this.name = name;
        this.price = new ProductPrice(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price.toLong();
    }
}
