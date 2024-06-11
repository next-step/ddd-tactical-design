package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuGroupCreationResponseDto;
import kitchenpos.menus.domain.menugroup.MenuGroup;
import kitchenpos.menus.application.dto.MenuGroupsCreationResponseDto;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
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
    menuGroupRepository.save(menuGroup);
    return MenuGroupCreationResponseDto.of(menuGroup);
  }

  @Transactional(readOnly = true)
  public MenuGroupsCreationResponseDto findAll() {
    return MenuGroupsCreationResponseDto.of(menuGroupRepository.findAll());
  }
}
