package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kitchenpos.core.constant.ExceptionMessages;

@Entity
@Table(name = "dt_orders")
public class EatInOrder {

	@Id
	@Column(name = "id", columnDefinition = "binary(16)")
	private UUID id;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(name = "order_date_time", nullable = false)
	private LocalDateTime orderDateTime;

	@Embedded
	private OrderLineItems orderLineItems;

	@ManyToOne(optional = false)
	@JoinColumn(
		name = "order_table_id",
		columnDefinition = "binary(16)",
		foreignKey = @ForeignKey(name = "fk_orders_to_order_table"),
		insertable = false, updatable = false
	)
	private OrderTable orderTable;

	protected EatInOrder() {
	}

	public EatInOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
		this(OrderStatus.WAITING, orderLineItems, orderTable);
	}

	EatInOrder(
		OrderStatus status,
		OrderLineItems orderLineItems,
		OrderTable orderTable
	) {
		validate(orderTable);

		this.id = UUID.randomUUID();
		this.orderDateTime = LocalDateTime.now();
		this.status = status;
		this.orderLineItems = orderLineItems;
		this.orderTable = orderTable;
	}

	private void validate(OrderTable orderTable) {
		if (orderTable.isUnoccupied()) {
			throw new IllegalArgumentException(ExceptionMessages.Order.EMPTY_ORDER_TABLE);
		}
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

	public OrderTable getOrderTable() {
		return orderTable;
	}
}
