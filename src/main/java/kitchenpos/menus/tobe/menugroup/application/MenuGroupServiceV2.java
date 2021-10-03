package kitchenpos.menus.tobe.menugroup.application;

import java.util.List;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.menugroup.domain.model.MenuGroupV2;
import kitchenpos.menus.tobe.menugroup.domain.repository.MenuGroupRepositoryV2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupServiceV2 {

    private final MenuGroupRepositoryV2 menuGroupRepositoryV2;

    public MenuGroupServiceV2(final MenuGroupRepositoryV2 menuGroupRepositoryV2) {
        this.menuGroupRepositoryV2 = menuGroupRepositoryV2;
    }

    @Transactional
    public MenuGroupV2 create(final DisplayedName displayedName) {
        final MenuGroupV2 menuGroup = new MenuGroupV2(displayedName);
        return menuGroupRepositoryV2.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupV2> findAll() {
        return menuGroupRepositoryV2.findAll();
    }
}
