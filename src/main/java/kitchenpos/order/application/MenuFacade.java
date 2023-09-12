package kitchenpos.order.application;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.tobe.application.port.out.LoadMenuPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class MenuFacade {

    private final LoadMenuPort loadMenuPort;

    public MenuFacade(LoadMenuPort loadMenuPort) {
        this.loadMenuPort = loadMenuPort;
    }

    List<Menu> findAllByIdIn(final List<UUID> ids) {
        return loadMenuPort.findAllByIdIn(ids);
    }

    Optional<Menu> findById(final UUID id) {
        return loadMenuPort.findById(id);
    }
}
