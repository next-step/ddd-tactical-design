package kitchenpos.menus.tobe.domain.application;

import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;
import kitchenpos.menus.tobe.dto.MenuGroupCreateDto;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface CreateMenuGroup {
    MenuGroup execute(MenuGroupCreateDto createMenuGroupDto);
}

@Service
class DefaultCreateMenuGroup implements CreateMenuGroup {
    private final MenuGroupRepository menuGroupRepository;

    public DefaultCreateMenuGroup(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Override
    public final MenuGroup execute(MenuGroupCreateDto createMenuGroupDto) {
        MenuGroupName menuGroupName = MenuGroupName.of(createMenuGroupDto.getName());
        final MenuGroup menuGroup = new MenuGroup(
            UUID.randomUUID(),
            menuGroupName
        );
        return menuGroupRepository.save(menuGroup);
    }
}
