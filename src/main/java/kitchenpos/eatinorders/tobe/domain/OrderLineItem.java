package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;

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
@Table(name = "dt_order_line_item")
public class OrderLineItem {

	@Id
	@Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(name = "menu_id", nullable = false)
	private String menuId;

	@Embedded
	private Price price;

	@Embedded
	private Quantity quantity;

	protected OrderLineItem() {
	}

	public OrderLineItem(String menuId, Price price, Quantity quantity) {
		this.menuId = menuId;
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrderLineItem that = (OrderLineItem)o;
		return Objects.equals(seq, that.seq) && Objects.equals(menuId, that.menuId) && Objects.equals(price, that.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(seq, menuId, price);
	}

	@Override
	public String toString() {
		return "OrderLineItem{" +
			"seq=" + seq +
			", menuId='" + menuId + '\'' +
			", price=" + price +
			", quantity=" + quantity +
			'}';
	}
}

