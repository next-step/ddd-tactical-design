package kitchenpos.eatinorders.ordertable.tobe.domain.vo.exception;

public class MinimumGuestOfNumbersException extends IllegalArgumentException {

    private static final String MESSAGE = "0명보다 적을 수 없습니다. value=%d";

    public MinimumGuestOfNumbersException(final int value) {
        super(String.format(MESSAGE, value));
    }
}
