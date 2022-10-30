package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllMenuService {
    private final MenuRepository menuRepository;

    public FindAllMenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
