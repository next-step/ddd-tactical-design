package kitchenpos.core.constant;

import kitchenpos.core.specification.NameSpecification;
import kitchenpos.core.specification.PriceSpecification;
import kitchenpos.core.specification.QuantitySpecification;

public final class Specs {

	private Specs() {
	}

	public static final class Menu {

		private Menu() {
		}

		public static final NameSpecification NAME = () -> UbiquitousLanguages.MENU;
		public static final PriceSpecification PRICE = () -> UbiquitousLanguages.MENU;
		public static final QuantitySpecification QUANTITY = () -> UbiquitousLanguages.MENU;
	}

	public static final class MenuGroup {

		private MenuGroup() {
		}

		public static final NameSpecification NAME = () -> UbiquitousLanguages.MENU_GROUP;
	}

	public static final class Product {

		private Product() {
		}

		public static final NameSpecification NAME = () -> UbiquitousLanguages.PRODUCT;
		public static final PriceSpecification PRICE = () -> UbiquitousLanguages.PRODUCT;
		public static final QuantitySpecification QUANTITY = () -> UbiquitousLanguages.MENU_PRODUCT;
	}
}
