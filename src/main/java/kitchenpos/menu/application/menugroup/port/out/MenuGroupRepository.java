package kitchenpos.menu.application.menugroup.port.out;

import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;

public interface MenuGroupRepository {

    MenuGroupNew save(final MenuGroupNew menuGroup);

    Optional<MenuGroupNew> findById(final UUID id);
}
