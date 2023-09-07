package kitchenpos.products.tobe.domain;

import kitchenpos.profanity.ProfanityClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private DisplayedName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice price;

    protected Product() {

    }
    public Product(UUID id, DisplayedName name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, ProfanityClient profanityClient, BigDecimal price){
        return new Product(
                UUID.randomUUID()
                , new DisplayedName(name, profanityClient)
                , new ProductPrice(price)
        );
    }

    public void changePrice(ProductPrice price){
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public BigDecimal getPriceValue(){
        return price.getValue();
    }

    public ProductPrice getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
