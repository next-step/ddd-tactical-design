package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;

public class FakeMenuUpdatePolicy implements MenuUpdatePolicy {

    private final MenuRepository menuRepository;

    public FakeMenuUpdatePolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void hideMenu(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(menu -> {
            menu.setDisplayed(false);
            menuRepository.save(menu);
        });
    }
}

