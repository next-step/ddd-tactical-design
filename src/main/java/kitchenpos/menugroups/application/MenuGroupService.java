package kitchenpos.menugroups.application;

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
        MenuGroup response = menuGroupRepository.save(request);
        return MenuGroupResponse.fromEntity(response);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        List<MenuGroup> responses = menuGroupRepository.findAll();
        return MenuGroupResponse.fromEntities(responses);
    }

    @Transactional(readOnly = true)
    public MenuGroupResponse findById(MenuGroupId menuGroupId) {
        MenuGroup response = menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
        return MenuGroupResponse.fromEntity(response);
    }

}
