package kitchenpos.menus.tobe.application;

import java.util.List;
import kitchenpos.menus.tobe.application.dto.MenuRequestDto;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuManager;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MenuService {

    private final MenuManager menuManager;
    private final MenuRepository menuRepository;

    public MenuService(MenuManager menuManager, MenuRepository menuRepository) {
        this.menuManager = menuManager;
        this.menuRepository = menuRepository;
    }

    public Long create(MenuRequestDto requestDto) {
        return menuManager.create(requestDto).getId();
    }

    public List<Menu> list() {
        return menuManager.list();
    }
}
