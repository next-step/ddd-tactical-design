package kitchenpos.menus.tobe.v2.application;

import kitchenpos.menus.tobe.v2.domain.MenuGroup;
import kitchenpos.menus.tobe.v2.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.v2.dto.MenuGroupRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroup create(MenuGroupRequestDto menuGroupRequestDto) {
        MenuGroup menuGroup = menuGroupRequestDto.toEntity();
        return menuGroupRepository.save(menuGroup);
    }

    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }
}
