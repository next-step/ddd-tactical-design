package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.UUID;

/**
 * Menu BC 로부터 Menu 정보를 받아오는 역할을 한다.
 * Repository Pattern 을 사용하지만,
 * RDB 에 한정하지 않고 NoSQL, Http 통신을 통해 데이터를 받아오는 것도 대응할 수 있도록 설계
 */
public interface EatInOrderMenuRepository {
    EatInOrderMenus findAllByIdIn(List<UUID> eatInOrderMenuIds);
}
