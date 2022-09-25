package kitchenpos.core.constant;

public final class ExceptionMessages {

	public static final class Product {

		public static final String NAME_IS_EMPTY = "상품 이름은 빈 값이 될 수 없습니다.";
		public static final String NAME_IS_PROFANITY = "상품 이름에 비속어가 포함되어 있습니다.";

		public static final String PRICE_IS_EMPTY = "상품 가격은 빈 값이 될 수 없습니다.";
		public static final String PRICE_IS_NEGATIVE = "가격은 0보다 작을 수 없습니다.";
	}
}
