package kitchenpos.orders.tobe.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("")
public class NullTypeOrder extends Order {
	protected NullTypeOrder() {
		super();
	}

	@Override
	public Order accepted(KitchenridersClient kitchenridersClient) {
		return null;
	}

	@Override
	public Order served() {
		return null;
	}

	@Override
	public Order completed() {
		return null;
	}

	public NullTypeOrder(
		OrderStatus status,
		OrderLineItems orderLineItems
	) {
		super(null, status, orderLineItems, null);
	}
}
