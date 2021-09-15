package kitchenpos.menugroups.application;

import kitchenpos.common.infra.Profanities;
import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.domain.MenuGroupName;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.dto.CreateMenuGroupRequest;
import kitchenpos.menugroups.dto.MenuGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("TobeMenuGroupService")
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;
    private final Profanities profanities;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository, final Profanities profanities) {
        this.menuGroupRepository = menuGroupRepository;
        this.profanities = profanities;
    }

    @Transactional
    public MenuGroupResponse create(final CreateMenuGroupRequest request) {
        final MenuGroupName menuGroupName = new MenuGroupName(request.getName(), profanities);
        return MenuGroupResponse.from(menuGroupRepository.save(new MenuGroup(menuGroupName)));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll()
                .stream().map(MenuGroupResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MenuGroupResponse findById(final UUID id) {
        final MenuGroup menuGroup = menuGroupRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return MenuGroupResponse.from(menuGroup);
    }
}
