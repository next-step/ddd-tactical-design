package kitchenpos.menus.application;

import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.dto.MenuGroupRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TobeMenuGroupService {
    private final TobeMenuGroupRepository menuGroupRepository;

    public TobeMenuGroupService(final TobeMenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public TobeMenuGroup create(final MenuGroupRegisterRequest request) {
        if(Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeMenuGroup menuGroup = new TobeMenuGroup.MenuGroupBuilder()
                .menuGroupName(request.getName())
                .namingRule(request.getNamingRule())
                .build();
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<TobeMenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
