package kitchenpos.core.constant;

public final class ExceptionMessages {

	public static final String NOT_FOUND_TEMPLATE = "%s을(를) 찾을 수 없습니다.";
	public static final String EMPTY_PRICE_TEMPLATE = "%s 가격은 빈 값이 될 수 없습니다.";
	public static final String NEGATIVE_PRICE_TEMPLATE = "%s 가격은 0보다 작을 수 없습니다.";
	public static final String EMPTY_QUANTITY_TEMPLATE = "%s 수량은 빈 값이 될 수 없습니다.";
	public static final String NEGATIVE_QUANTITY_TEMPLATE = "%s 수량은 0보다 작을 수 없습니다.";
	public static final String EMPTY_NAME_TEMPLATE = "%s 이름은 빈 값이 될 수 없습니다.";
	public static final String PROFANITY_NAME_TEMPLATE = "%s 이름에 비속어가 포함되어 있습니다.";
	public static final String EMPTY_INVENTORY_TEMPLATE = "%s 목록은 빈 값이 될 수 없습니다.";

	private ExceptionMessages() {
	}

	public static final class Menu {

		public static final String INVALID_PRICE = "메뉴의 가격이 메뉴 상품의 금액 총합보다 높을 수 없습니다.";

		private Menu() {
		}
	}

	public static final class Order {

		public static final String INVALID_ORDER_STATUS = "현재 주문 상태에서 수행할 수 없습니다.";
		public static final String EMPTY_ORDER_TABLE = "빈 주문 테이블에서는 손님 수를 변경할 수 없습니다.";

		private Order() {
		}
	}
}
