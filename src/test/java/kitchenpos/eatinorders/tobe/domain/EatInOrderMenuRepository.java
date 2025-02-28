package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;

public interface EatInOrderMenuRepository {
    EatInOrderMenus findAllByIdIn(List<UUID> eatInOrderMenuIds);
}
