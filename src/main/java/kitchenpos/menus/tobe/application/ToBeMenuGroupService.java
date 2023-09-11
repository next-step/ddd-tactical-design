package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.tobe.domain.ToBeMenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ToBeMenuGroupService {
    private final ToBeMenuGroupRepository menuGroupRepository;

    public ToBeMenuGroupService(final ToBeMenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public ToBeMenuGroup create(final ToBeMenuGroup request) {
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final ToBeMenuGroup menuGroup = new ToBeMenuGroup(name);
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<ToBeMenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
