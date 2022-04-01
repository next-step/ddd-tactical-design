package kitchenpos.menus.tobe.domain.exception;

public class ViolationOfMenuPricePolicyException extends IllegalStateException {

    private static final String DEFAULT_MESSAGE = "메뉴 가격 정책을 위반하였습니다. 메뉴의 가격은 상품들의 가격보다 같거나 낮아야합니다.";

    public ViolationOfMenuPricePolicyException() {
        super(DEFAULT_MESSAGE);
    }

    public ViolationOfMenuPricePolicyException(String message) {
        super(message);
    }
}
