package kitchenpos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menugroups.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.eatinorders.tobe.application.dto.OrderCreationRequest;
import kitchenpos.eatinorders.tobe.application.dto.OrderLineItemCreationRequest;
import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderType;
import kitchenpos.products.tobe.application.dto.ProductCreationRequest;
import kitchenpos.products.tobe.domain.Product;

public class TobeFixtures {

	public static final UUID INVALID_ID = new UUID(0L, 0L);

	public static Menu menu() {
		return menu(UUID.randomUUID(), 19_000L, true, menuProduct());
	}

	public static Menu menu(UUID id) {
		return menu(id, 19_000L, true, menuProduct());
	}

	public static Menu menu(final long price, final MenuProduct... menuProducts) {
		return menu(UUID.randomUUID(), price, true, menuProducts);
	}

	public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
		return menu(UUID.randomUUID(), price, displayed, menuProducts);
	}

	public static Menu menu(final UUID id, final long price, final MenuProduct... menuProducts) {
		return menu(id, price, true, menuProducts);
	}

	public static Menu menu(final UUID id, final long price, final boolean displayed, final MenuProduct... menuProducts) {
		return new Menu(
			id,
			"후라이드+후라이드",
			BigDecimal.valueOf(price),
			menuGroup(),
			List.of(menuProducts),
			displayed
		);
	}

	public static MenuGroup menuGroup() {
		return menuGroup("두마리메뉴");
	}

	public static MenuGroup menuGroup(final String name) {
		return new MenuGroup(UUID.randomUUID(), name);
	}

	public static MenuProduct menuProduct() {
		return new MenuProduct(new Random().nextLong(), product(), 2L);
	}

	public static MenuProduct menuProduct(final Product product, final long quantity) {
		return new MenuProduct(new Random().nextLong(), product, quantity);
	}

	public static Product product() {
		return product("후라이드", 16_000L);
	}

	public static Product product(final String name, final long price) {
		return new Product(name, BigDecimal.valueOf(price));
	}

	public static OrderCreationRequest orderCreationRequest(OrderType type, UUID menuId) {
		List<OrderLineItemCreationRequest> orderLineItemRequests = List.of(
			createOrderLineItemRequest(type, menuId, 19_000L, 3L));

		if (type == OrderType.EAT_IN) {
			return new OrderCreationRequest(
				OrderType.EAT_IN,
				orderLineItemRequests,
				"서울시 송파구 위례성대로 2",
				orderTable(4, true).getId()
			);
		}

		return new OrderCreationRequest(
			null,
			orderLineItemRequests,
			null,
			null
		);
	}

	public static OrderLineItemCreationRequest createOrderLineItemRequest(final OrderType orderType, final UUID menuId, final long price, final long quantity) {
		return new OrderLineItemCreationRequest(
			orderType,
			menuId,
			BigDecimal.valueOf(price),
			quantity
		);
	}

	public static EatInOrder eatInOrder(final OrderStatus status, final OrderTable orderTable) {
		OrderLineItemCreationRequest request = orderLineItemCreationRequest(OrderType.EAT_IN);
		Menu menu = menu(request.menuId());

		return new EatInOrder(
			status,
			LocalDateTime.of(2020, 1, 1, 12, 0),
			OrderLineItems.fromRequests(List.of(request), Map.of(request.menuId(), menu)),
			orderTable
		);
	}

	public static OrderLineItemCreationRequest orderLineItemCreationRequest(OrderType orderType) {
		return new OrderLineItemCreationRequest(
			orderType,
			menu().getId(),
			BigDecimal.valueOf(19_000L),
			1
		);
	}

	public static OrderTable orderTable() {
		return orderTable(0, false);
	}

	public static OrderTable orderTable(final int numberOfGuests, final boolean occupied) {
		return new OrderTable(
			UUID.randomUUID(),
			"1번",
			numberOfGuests,
			occupied
		);
	}
}

