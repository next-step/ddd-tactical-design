package kitchenpos.deliveryorders.domain;

import kitchenpos.menus.tobe.domain.menu.Menu;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuClient {

    Optional<Menu> findById(UUID id);
    List<Menu> findAllByIdIn(List<UUID> ids);
}
