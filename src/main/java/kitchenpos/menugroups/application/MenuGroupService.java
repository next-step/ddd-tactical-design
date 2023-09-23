package kitchenpos.menugroups.application;

import kitchenpos.menugroups.application.mapper.MenuGroupMapper;
import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.domain.MenuGroupId;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.dto.MenuGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupResponse create(final MenuGroup request) {
        MenuGroup menuGroup = menuGroupRepository.save(request);
        return MenuGroupMapper.toDto(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        List<MenuGroup> menuGroups = menuGroupRepository.findAll();
        return MenuGroupMapper.toDtos(menuGroups);
    }

    @Transactional(readOnly = true)
    public MenuGroupResponse findById(MenuGroupId menuGroupId) {
        MenuGroup menuGroup = menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
        return MenuGroupMapper.toDto(menuGroup);
    }

}
