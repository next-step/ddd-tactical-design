package kitchenpos.menu.application.facade;

import kitchenpos.menu.domain.service.MenuGroupService;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;
import kitchenpos.menu.domain.service.MenuService;
import org.springframework.stereotype.Component;

@Component
public class MenuProductFacade {

    private final MenuService menuService;
    private final MenuGroupService menuGroupService;
    private final MenuPurgomalumClient purgomalumClient;

    public MenuProductFacade(MenuService menuService, MenuGroupService menuGroupService,
        MenuPurgomalumClient purgomalumClient) {
        this.menuService = menuService;
        this.menuGroupService = menuGroupService;
        this.purgomalumClient = purgomalumClient;
    }
}
