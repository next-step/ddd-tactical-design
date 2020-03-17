package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    protected Product (){}

    public Product(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.valueOf();
    }

    public BigDecimal getPrice() {
        return this.price.valueOf();
    }

    public static class Builder {
        private Long id;
        private ProductName name;
        private ProductPrice price;

        public Builder id (Long id){
            this.id = id;
            return this;
        }

        public Builder name (String name){
            this.name = new ProductName(name);
            return this;
        }

        public Builder price (BigDecimal price){
            this.price = new ProductPrice(price);
            return this;
        }

        public Product build (){
            return new Product(this);
        }
    }


}
