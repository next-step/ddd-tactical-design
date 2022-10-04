package kitchenpos.eatinorders.tobe.domain;

public enum OrderStatus {

	WAITING, ACCEPTED, SERVED, DELIVERING, DELIVERED, COMPLETED;

	public boolean isDifferentWith(OrderStatus orderStatus) {
		return this != orderStatus;
	}
}
