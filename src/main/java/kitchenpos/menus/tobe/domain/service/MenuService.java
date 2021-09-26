package kitchenpos.menus.tobe.domain.service;

import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.model.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public void validateMenu(Menu menu) {
        menuGroupRepository.findById(menu.getMenuGroupId()).orElseThrow(() -> new IllegalArgumentException("메뉴는 특정 메뉴 그룹에 속해야 합니다."));
    }

}
