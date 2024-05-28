package kitchenpos.menus.tobe.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.service.dto.MenuGroupCreationResponseDto;
import kitchenpos.menus.tobe.service.dto.MenuGroupsCreationResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupCreationResponseDto create(String name) {
        final MenuGroup menuGroup = MenuGroup.create(name);
        return MenuGroupCreationResponseDto.of(menuGroup);
    }

    @Transactional(readOnly = true)
    public MenuGroupsCreationResponseDto findAll() {
        return MenuGroupsCreationResponseDto.of(menuGroupRepository.findAll());
    }
}
