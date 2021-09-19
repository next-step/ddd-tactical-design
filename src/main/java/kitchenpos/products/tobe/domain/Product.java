package kitchenpos.products.tobe.domain;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(final Name name, final Price price) {
        this.name = name;
        this.price = price;
    }

    public void changePrice(final Product product) {
        this.price = product.price;
    }


}
