package kitchenpos.products.tobe;

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

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	@Embedded
	private ProductPrice price;

	protected Product() {
	}

	public Product(UUID id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = new ProductPrice(price);
	}

	public void changePrice(int price) {
		this.price = new ProductPrice(price);
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProductPrice getPrice() {
		return price;
	}
}
