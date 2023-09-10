package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private final String value;


    protected Name() {
        throw new KitchenPosException("DEFAULT 생성자 호출", KitchenPosExceptionType.METHOD_NOT_ALLOWED);
    }

    public Name(final String value) {
        validate(value);
        this.value = value;
    }

    private static void validate(String value) {
        if (value == null || value.isEmpty()) {
            String message = String.format("이름이 %s 이므로", value);
            throw new KitchenPosException(message, KitchenPosExceptionType.BAD_REQUEST);
        }
    }

    public String getValue() {
        return value;
    }

}
