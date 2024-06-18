package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TobeMenuGroupService {
    private final TobeMenuGroupRepository menuGroupRepository;

    public TobeMenuGroupService(final TobeMenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public TobeMenuGroup create(final String name) {
        TobeMenuGroup menuGroup = TobeMenuGroup.create(name);
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<TobeMenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
