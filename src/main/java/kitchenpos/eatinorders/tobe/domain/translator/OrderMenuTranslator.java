package kitchenpos.eatinorders.tobe.domain.translator;

import kitchenpos.eatinorders.tobe.domain.model.OrderMenu;

import java.util.List;
import java.util.UUID;

public interface OrderMenuTranslator {

    List<OrderMenu> findAllByIdIn(final List<UUID> ids);
}
