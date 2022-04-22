package kitchenpos.menus.application;

import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.dto.MenuGroupDto;
import kitchenpos.menus.dto.MenuGroupRegisterRequest;
import kitchenpos.menus.dto.MenuGroupRegisterResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TobeMenuGroupService {
    private final TobeMenuGroupRepository menuGroupRepository;

    public TobeMenuGroupService(final TobeMenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupRegisterResponse create(final MenuGroupRegisterRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeMenuGroup menuGroup = new TobeMenuGroup.MenuGroupBuilder()
                .menuGroupName(request.getName())
                .build();
        return new MenuGroupRegisterResponse(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupDto> findAll() {
        return menuGroupRepository.findAll().stream().map(MenuGroupDto::new).collect(Collectors.toList());
    }
}
