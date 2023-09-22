package kitchenpos.menu.application.menu.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.domain.menu.MenuNew;

public interface MenuRepository {

    MenuNew save(final MenuNew menu);

    Optional<MenuNew> findById(final UUID id);

    List<MenuNew> findAll();

    List<MenuNew> findAllByProductId(final UUID productId);
}
