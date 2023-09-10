package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static kitchenpos.common.ComparisonUtils.lessThan;

@Embeddable
public class Price {

    private final BigDecimal value;


    protected Price() {
        throw new KitchenPosException("DEFAULT 생성자 호출", KitchenPosExceptionType.METHOD_NOT_ALLOWED);
    }

    public Price(final Long value) {
        this(BigDecimal.valueOf(value));
    }
    public Price(final BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private static void validate(BigDecimal value) {
        if (value == null || lessThan(value, ZERO)) {
            String message = String.format("이름이 %s 이므로", value);
            throw new KitchenPosException(message, KitchenPosExceptionType.BAD_REQUEST);
        }
    }

    public BigDecimal getValue() {
        return value;
    }

}
