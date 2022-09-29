package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.core.domain.Price;
import kitchenpos.core.domain.Quantity;

@Entity
@Table(name = "dt_menu_product")
public class MenuProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")
	private Long seq;

	@Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
	private UUID menuId;

	@Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
	private UUID productId;

	@Embedded
	private Price price;

	@Embedded
	private Quantity quantity;

	protected MenuProduct() {
	}

	public MenuProduct(
		UUID menuId,
		UUID productId,
		Price price,
		Quantity quantity
	) {
		this.menuId = menuId;
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
	}

	public BigDecimal getTotalPrice() {
		return price.multiply(quantity);
	}

	public void changePrice(Price price) {
		this.price = price;
	}

	public void changeQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public Long getSeq() {
		return seq;
	}

	public UUID getMenuId() {
		return menuId;
	}

	public UUID getProductId() {
		return productId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MenuProduct that = (MenuProduct)o;
		return Objects.equals(getSeq(), that.getSeq());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSeq());
	}
}
