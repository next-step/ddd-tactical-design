package kitchenpos.menus.application;

import kitchenpos.menus.shared.dto.MenuGroupDto;
import kitchenpos.menus.shared.dto.request.MenuGroupCreateRequest;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.shared.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupDto create(final MenuGroupCreateRequest request) {
        final MenuGroup menuGroup = MenuGroup.from(new MenuGroupName(request.getName()));
        return ConvertUtil.convert(menuGroupRepository.save(menuGroup), MenuGroupDto.class);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupDto> findAll() {
        return ConvertUtil.convertList(menuGroupRepository.findAll(), MenuGroupDto.class);
    }
}
