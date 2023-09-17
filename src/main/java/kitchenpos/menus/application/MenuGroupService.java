package kitchenpos.menus.application;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.values.Name;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.dto.CreateRequest;
import kitchenpos.menus.dto.MenuGroupDto;
import kitchenpos.menus.tobe.domain.ToBeMenuGroup;
import kitchenpos.menus.tobe.domain.ToBeMenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuGroupService {
    private final ToBeMenuGroupRepository toBeMenuGroupRepository;
    private final Purgomalum purgomalum;

    public MenuGroupService(ToBeMenuGroupRepository toBeMenuGroupRepository,
                            Purgomalum purgomalum) {
        this.toBeMenuGroupRepository = toBeMenuGroupRepository;
        this.purgomalum = purgomalum;
    }

    @Transactional
    public MenuGroupDto create(final CreateRequest request) {
        Name name = new Name(request.getName(), purgomalum);
        final ToBeMenuGroup menuGroup = new ToBeMenuGroup(name);
        ToBeMenuGroup savedResult = toBeMenuGroupRepository.save(menuGroup);
        return MenuGroupDto.from(savedResult);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupDto> findAll() {
        return toBeMenuGroupRepository.findAll()
          .stream()
          .map(MenuGroupDto::from)
          .collect(Collectors.toList());
    }
}
