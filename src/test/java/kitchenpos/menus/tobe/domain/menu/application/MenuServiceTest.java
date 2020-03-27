package kitchenpos.menus.tobe.domain.menu.application;

import kitchenpos.menus.tobe.domain.menu.infra.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.infra.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.service.DefaultMenuService;
import kitchenpos.menus.tobe.domain.menu.service.MenuRegisterService;
import kitchenpos.menus.tobe.domain.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;

public class MenuServiceTest {

    private MenuService menuService;
    private MenuRegisterService menuRegisterService;

    private final MenuRepository menuRepository = new InMemoryMenuRepository();


    @BeforeEach
    void setup(){
        menuService = new DefaultMenuService(menuRegisterService, menuRepository);
    }


}
