package kitchenpos.products.tobe.domain;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product")
@Entity(name = "TobeProduct")
public class Product {
	@Id
	@Column(name = "id", columnDefinition = "varbinary(16)")
	private UUID id;
	@Embedded
	private DisplayedName name;
	@Embedded
	private Price price;

	protected Product() {

	}

	public Product(final DisplayedName name, final Price price) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.price = price;
	}

	public void changePrice(final Price price) {
		this.price = price;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final Product product = (Product)o;
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
