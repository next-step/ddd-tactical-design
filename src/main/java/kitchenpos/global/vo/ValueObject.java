package kitchenpos.global.vo;

import java.io.Serializable;

public abstract class ValueObject implements Serializable {

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
