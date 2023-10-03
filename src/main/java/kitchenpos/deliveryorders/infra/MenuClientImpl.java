package kitchenpos.deliveryorders.infra;

import kitchenpos.deliveryorders.domain.MenuClient;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuClientImpl implements MenuClient {
    private final MenuRepository menuRepository;

    public MenuClientImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return menuRepository.findById(id);
    }

    @Override
    public int countAllByIdIn(List<UUID> ids) {
        return menuRepository.findAllByIdIn(ids).size();
    }
}
