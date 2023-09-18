package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.Price;

import java.util.UUID;

public interface OrderLinePolicy {
    void validate(UUID menuId, Price price);
}
