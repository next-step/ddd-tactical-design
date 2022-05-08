package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product")
@Entity
public class Product {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

	@Embedded
    private Price price;

    public Product() {
    }

    public Product(Name name, Price price) {
    	this.id = UUID.randomUUID();
    	this.name = name;
    	this.price = price;
	}

	public static Product create(Name name, Price price) {
    	Product product = new Product();
		product.id = UUID.randomUUID();
		product.name = name;
		product.price = price;

		return product;
	}

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public void changePrice(final Price price) {
        this.price = price;
    }
}
