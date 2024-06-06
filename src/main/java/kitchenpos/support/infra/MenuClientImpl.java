package kitchenpos.support.infra;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.domain.MenuClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class MenuClientImpl implements MenuClient {
    private final MenuRepository menuRepository;

    public MenuClientImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public int countMenusByIdIn(List<UUID> menuIds) {
        return menuRepository.findAllByIdIn(menuIds).size();
    }
}
