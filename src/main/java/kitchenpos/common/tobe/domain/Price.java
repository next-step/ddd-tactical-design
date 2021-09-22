package kitchenpos.common.tobe.domain;

import java.math.BigDecimal;
import kitchenpos.menus.tobe.domain.model.Quantity;

public interface Price extends Comparable<Price> {

    BigDecimal getValue();

    Price multiply(final Quantity quantity);

    Price add(final Price price);

}
