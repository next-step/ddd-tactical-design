package kitchenpos.menus.tobe.menugroup.application;

import kitchenpos.menus.tobe.menugroup.dto.MenuGroupDto;
import kitchenpos.menus.tobe.menugroup.exception.MenuGroupDuplicationException;
import kitchenpos.menus.tobe.menugroup.infra.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DefaultMenuGroupService implements MenuGroupService{

    private final MenuGroupRepository menuGroupRepository;

    public DefaultMenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Override
    @Transactional
    public MenuGroupDto register(MenuGroupDto dto) {
        validateRegisteredMenuGroup(dto.getName());

        return null;
    }

    @Override
    public List<MenuGroupDto> list() {
        return null;
    }

    @Override
    public Optional<MenuGroupDto> findMenuGroup(Long id) {
        return Optional.empty();
    }

    protected void validateRegisteredMenuGroup (final String name){
        if(menuGroupRepository.findByNameContaining(name)){
            throw new MenuGroupDuplicationException("동일한 메뉴그룹이 이미 등록 되어있습니다.");
        }
    }

}
