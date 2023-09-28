package kitchenpos.deliveryorders.application;

import kitchenpos.deliveryorders.domain.MenuClient;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryMenuClient implements MenuClient {

    private final MenuRepository menuRepository;

    public InMemoryMenuClient(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return menuRepository.findById(id);
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return menuRepository.findAllByIdIn(ids);
    }
}
