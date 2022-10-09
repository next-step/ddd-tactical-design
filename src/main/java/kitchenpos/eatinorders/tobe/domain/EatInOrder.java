package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.core.constant.ExceptionMessages;

@Entity
@Table(name = "dt_orders")
public class EatInOrder {

	@Id
	@Column(name = "id", columnDefinition = "binary(16)")
	private UUID id;

	@Column(name = "order_table_id", columnDefinition = "binary(16)", nullable = false)
	private UUID orderTableId;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(name = "order_date_time", nullable = false)
	private LocalDateTime orderDateTime;

	@Embedded
	private OrderLineItems orderLineItems;

	protected EatInOrder() {
	}

	EatInOrder(OrderLineItems orderLineItems, UUID orderTableId) {
		this(OrderStatus.WAITING, orderLineItems, orderTableId);
	}

	EatInOrder(
		OrderStatus status,
		OrderLineItems orderLineItems,
		UUID orderTableId
	) {
		this.id = UUID.randomUUID();
		this.orderDateTime = LocalDateTime.now();
		this.status = status;
		this.orderLineItems = orderLineItems;
		this.orderTableId = orderTableId;
	}

	public void accept() {
		changeStatus(OrderStatus.WAITING, OrderStatus.ACCEPTED);
	}

	public void serve() {
		changeStatus(OrderStatus.ACCEPTED, OrderStatus.SERVED);
	}

	public void complete() {
		changeStatus(OrderStatus.SERVED, OrderStatus.COMPLETED);
	}

	private void changeStatus(OrderStatus current, OrderStatus next) {
		if (status.isDifferentWith(current)) {
			throw new IllegalStateException(ExceptionMessages.Order.INVALID_ORDER_STATUS);
		}
		status = next;
	}

	public boolean isCompleted() {
		return status == OrderStatus.COMPLETED;
	}

	public UUID getId() {
		return id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public OrderLineItems getOrderLineItems() {
		return orderLineItems;
	}
}
