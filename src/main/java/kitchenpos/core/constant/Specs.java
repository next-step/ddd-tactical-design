package kitchenpos.core.constant;

import kitchenpos.core.specification.NameSpecification;
import kitchenpos.core.specification.PriceSpecification;
import kitchenpos.core.specification.QuantitySpecification;

public final class Specs {

	private Specs() {
	}

	public static final class Menu {

		public static final NameSpecification NAME = () -> UbiquitousLanguages.MENU;
		public static final PriceSpecification PRICE = () -> UbiquitousLanguages.MENU;
		public static final QuantitySpecification QUANTITY = () -> UbiquitousLanguages.MENU;

		private Menu() {
		}
	}

	public static final class MenuProduct {

		public static final PriceSpecification PRICE = () -> UbiquitousLanguages.MENU_PRODUCT;
		public static final QuantitySpecification QUANTITY = () -> UbiquitousLanguages.MENU_PRODUCT;

		private MenuProduct() {
		}
	}

	public static final class MenuGroup {

		public static final NameSpecification NAME = () -> UbiquitousLanguages.MENU_GROUP;

		private MenuGroup() {
		}
	}

	public static final class Product {

		public static final PriceSpecification PRICE = () -> UbiquitousLanguages.PRODUCT;
		public static final QuantitySpecification QUANTITY = () -> UbiquitousLanguages.PRODUCT;

		private Product() {
		}
	}

	public static final class Order {

		public static final NameSpecification NAME = () -> UbiquitousLanguages.ORDER;
		public static final PriceSpecification PRICE = () -> UbiquitousLanguages.ORDER;
		public static final QuantitySpecification QUANTITY = () -> UbiquitousLanguages.ORDER;

		private Order() {
		}
	}

	public static final class OrderTable {

		public static final NameSpecification NAME = () -> UbiquitousLanguages.ORDER_TABLE;

		private OrderTable() {
		}
	}

	public static final class OrderLineItem {

		public static final PriceSpecification PRICE = () -> UbiquitousLanguages.ORDER_LINE_ITEM;
		public static final QuantitySpecification QUANTITY = () -> UbiquitousLanguages.ORDER_LINE_ITEM;

		private OrderLineItem() {
		}
	}
}
