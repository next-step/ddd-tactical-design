package kitchenpos.eatinorders.application.eatinorder.port.out;

import java.util.List;
import java.util.UUID;

public interface ValidMenuPort {

    /**
     * @throws IllegalStateException orderLineItem에 있는 menu가 없는 menu일 때
     */
    void checkValidMenu(final List<UUID> menuIds);
}
