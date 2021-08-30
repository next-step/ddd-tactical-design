package kitchenpos.products.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;

@Table(name = "product")
@Entity
public class Product {
	@Column(name = "id", columnDefinition = "varbinary(16)")
	@Id
	private UUID id;
	@Embedded
	private DisplayedName name;
	@Embedded
	private Price price;

	protected Product() {

	}

	public Product(DisplayedName name, Price price) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.price = price;
	}

	public void changePrice(Price price) {
		this.price = price;
	}

	public UUID getId() {
		return id;
	}

	public DisplayedName getName() {
		return name;
	}

	public Price getPrice() {
		return price;
	}
}
