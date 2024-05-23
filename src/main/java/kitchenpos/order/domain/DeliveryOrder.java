package kitchenpos.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DELIVERY")
public class DeliveryOrder extends Order {
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "value", column = @Column(name = "delivery_address", nullable = false))
	})
	private DeliveryAddress deliveryAddress;

	protected DeliveryOrder() {
		super(OrderType.DELIVERY);
	}

	public DeliveryOrder(
		OrderStatus status,
		LocalDateTime orderDateTime,
		OrderLineItems orderLineItems,
		String deliveryAddress
	) {
		super(OrderType.DELIVERY, status, orderDateTime, orderLineItems, null);
		this.deliveryAddress = new DeliveryAddress(deliveryAddress);
	}

	public String getDeliveryAddress() {
		return deliveryAddress.getValue();
	}

	@Override
	public Order accepted(KitchenridersClient kitchenridersClient) {
		if (status != OrderStatus.WAITING) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.ACCEPTED;
		BigDecimal sum = getOrderLineItems().stream()
			.map(item -> item.getMenu().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		kitchenridersClient.requestDelivery(getId(), sum, getDeliveryAddress());
		return this;
	}

	@Override
	public Order served() {
		if (status != OrderStatus.ACCEPTED) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.SERVED;
		return this;
	}

	@Override
	public Order delivering() {
		if (status != OrderStatus.SERVED) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.DELIVERING;
		return this;
	}

	@Override
	public Order delivered() {
		if (status != OrderStatus.DELIVERING) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.DELIVERED;
		return this;
	}

	@Override
	public Order completed(OrderRepository orderRepository) {
		if (status != OrderStatus.DELIVERED) {
			throw new IllegalStateException(INVALID_ORDER_STATUS_ERROR);
		}
		status = OrderStatus.COMPLETED;
		return this;
	}
}


