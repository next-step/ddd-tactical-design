package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product")
@Entity
public class ToBeProduct {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "displayed name", nullable = false)
    @Embedded
    private ProductName productName;

    @Column(name = "price", nullable = false)
    @Embedded
    private ProductPrice productPrice;

    public ToBeProduct() {

    }

    public ToBeProduct(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.productName = new ProductName(name, purgomalumClient);
        this.productPrice = new ProductPrice(price);
    }

    public ToBeProduct(BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.productName = new ProductName("test", purgomalumClient);
        this.productPrice = new ProductPrice(price);
    }

    public void changePrice(BigDecimal price){
        this.productPrice.checkPrice(price);
    }

    public void checkDisplayedName(String name){
        this.productName.checkDisplayedName(name);

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return productName.getDisplayedName();
    }

    public BigDecimal getPrice() {
        return productPrice.getPrice();
    }

}

