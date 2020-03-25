package kitchenpos.products.tobe.infra;

import kitchenpos.common.Name;
import kitchenpos.common.Price;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected ProductEntity(){}

    public ProductEntity(Builder builder){
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
        private Name name;
        private Price price;

        public Builder id (Long id){
            this.id = id;
            return this;
        }

        public Builder name (String name){
            this.name = new Name(name);
            return this;
        }

        public Builder price (BigDecimal price){
            this.price = new Price(price);
            return this;
        }

        public ProductEntity build (){
            return new ProductEntity(this);
        }
    }


}
