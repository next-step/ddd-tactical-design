package kitchenpos.global.vo;

import java.io.Serializable;

public interface ValueObject extends Serializable {

    boolean equals(Object o);
    int hashCode();
}
