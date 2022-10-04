package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.core.CoreFixtures;
import kitchenpos.core.constant.Specs;
import kitchenpos.core.domain.Name;
import kitchenpos.core.domain.Price;
import kitchenpos.core.domain.Quantity;

public final class OrderFixtures {

	public static final String ORDER_TABLE_NAME = "테이블-1";
	public static final String OCCUPIED_ORDER_TABLE_NAME = "테이블-2";
	public static final String UNOCCUPIED_ORDER_TABLE_NAME = "테이블-3";

	public static final String MENU_ID = "1";
	public static final long PRICE = 250_000L;
	public static final int QUANTITY = 35;
	public static final int NUMBER_OF_GUESTS = 3;

	private OrderFixtures() {
	}

	public static EatInOrder eatInOrder(OrderStatus status) {
		return new EatInOrder(status, orderLineItems(), occupiedOrderTable(NUMBER_OF_GUESTS));
	}

	public static OrderLineItem orderLineItem() {
		return orderLineItem(MENU_ID, PRICE, QUANTITY);
	}

	public static OrderLineItem orderLineItem(String menuId, long price, long quantity) {
		return new OrderLineItem(menuId, orderLineItemPrice(price), orderLineItemQuantity(quantity));
	}

	public static OrderLineItems orderLineItems() {
		return orderLineItems(orderLineItem(), orderLineItem());
	}

	public static OrderLineItems orderLineItems(OrderLineItem item, OrderLineItem... items) {
		return new OrderLineItems(item, items);
	}

	public static OrderTable unoccupiedOrderTable() {
		return new OrderTable(orderTableName(UNOCCUPIED_ORDER_TABLE_NAME));
	}

	public static OrderTable occupiedOrderTable(int numberOfGuests) {
		OrderTable orderTable = new OrderTable(orderTableName(OCCUPIED_ORDER_TABLE_NAME));
		orderTable.sit(numberOfGuests);
		return orderTable;
	}

	public static Name orderTableName(String value) {
		return CoreFixtures.name(value, Specs.OrderTable.NAME);
	}

	public static Price orderLineItemPrice(long value) {
		return CoreFixtures.price(value, Specs.OrderLineItem.PRICE);
	}

	private static Quantity orderLineItemQuantity(long value) {
		return CoreFixtures.quantity(value, Specs.OrderLineItem.QUANTITY);
	}
}
