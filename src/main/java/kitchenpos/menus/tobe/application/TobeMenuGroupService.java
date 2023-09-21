package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.application.dto.TobeMenuGroupResponse;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupName;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TobeMenuGroupService {
    private final TobeMenuGroupRepository tobeMenuGroupRepository;

    public TobeMenuGroupService(final TobeMenuGroupRepository tobeMenuGroupRepository) {
        this.tobeMenuGroupRepository = tobeMenuGroupRepository;
    }

    @Transactional
    public TobeMenuGroupResponse create(final String name) {
        TobeMenuGroupName tobeMenuGroupName = new TobeMenuGroupName(name);
        final TobeMenuGroup menuGroup = new TobeMenuGroup(UUID.randomUUID(), tobeMenuGroupName);
        TobeMenuGroup savedMenuGroup = tobeMenuGroupRepository.save(menuGroup);
        return TobeMenuGroupResponse.of(savedMenuGroup);
    }

    @Transactional(readOnly = true)
    public List<TobeMenuGroupResponse> findAll() {
        return tobeMenuGroupRepository.findAll().stream()
                                      .map(TobeMenuGroupResponse::of)
                                      .collect(Collectors.toList());
    }
}
