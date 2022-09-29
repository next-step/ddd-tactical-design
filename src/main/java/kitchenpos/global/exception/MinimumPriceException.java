package kitchenpos.global.exception;

public class MinimumPriceException  extends IllegalArgumentException {

    public MinimumPriceException() {
        super("가격은 0원 이상이어야 한다.");
    }
}
