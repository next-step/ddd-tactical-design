package kitchenpos.menus.application;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.values.Name;
import kitchenpos.menus.dto.CreateRequest;
import kitchenpos.menus.dto.MenuGroupDto;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;
    private final Purgomalum purgomalum;

    public MenuGroupService(MenuGroupRepository menuGroupRepository,
                            Purgomalum purgomalum) {
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalum = purgomalum;
    }

    @Transactional
    public MenuGroupDto create(final CreateRequest request) {
        Name name = new Name(request.getName(), purgomalum);
        final MenuGroup menuGroup = new MenuGroup(name);
        MenuGroup savedResult = menuGroupRepository.save(menuGroup);
        return MenuGroupDto.from(savedResult);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupDto> findAll() {
        return menuGroupRepository.findAll()
          .stream()
          .map(MenuGroupDto::from)
          .collect(Collectors.toList());
    }
}
