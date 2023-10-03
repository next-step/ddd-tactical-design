package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.tobe.domain.order.EatInMenuClient;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EatInMenuClientImpl implements EatInMenuClient {

    private final MenuRepository menuRepository;

    public EatInMenuClientImpl(MenuRepository menuRepository) {
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
