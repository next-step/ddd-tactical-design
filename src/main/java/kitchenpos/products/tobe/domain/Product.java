package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.core.domain.Name;
import kitchenpos.core.domain.Price;
import kitchenpos.core.specification.NameSpecification;
import kitchenpos.core.specification.PriceSpecification;

@Table(name = "dt_product")
@Entity
public class Product {

	@Column(name = "id", columnDefinition = "binary(16)")
	@Id
	private UUID id;

	@Embedded
	private Name name;

	@Embedded
	private Price price;

	protected Product() {
	}

	public Product(String name, NameSpecification nameSpecification, BigDecimal price, PriceSpecification priceSpecification) {
		this.id = UUID.randomUUID();
		this.name = new Name(name, nameSpecification);
		this.price = new Price(price, priceSpecification);
	}

	public UUID getId() {
		return id;
	}

	public void changeName(String name, NameSpecification nameSpecification) {
		this.name = new Name(name, nameSpecification);
	}

	public void changePrice(BigDecimal price, PriceSpecification priceSpecification) {
		this.price = new Price(price, priceSpecification);
	}

	public String getName() {
		return name.getValue();
	}

	public BigDecimal getPrice() {
		return price.getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Product product = (Product)o;
		return Objects.equals(getId(), product.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
