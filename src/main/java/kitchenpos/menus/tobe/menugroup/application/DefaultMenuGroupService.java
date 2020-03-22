package kitchenpos.menus.tobe.menugroup.application;

import kitchenpos.common.WrongNameException;
import kitchenpos.menus.tobe.menugroup.dto.MenuGroupDto;
import kitchenpos.menus.tobe.menugroup.entity.MenuGroup;
import kitchenpos.menus.tobe.menugroup.exception.MenuGroupDuplicationException;
import kitchenpos.menus.tobe.menugroup.infra.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultMenuGroupService implements MenuGroupService{

    private final MenuGroupRepository menuGroupRepository;

    public DefaultMenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Override
    @Transactional
    public MenuGroupDto register(MenuGroupDto dto) {
        validateRegisteredMenuGroup(dto.getName());
        MenuGroup menuGroup = new MenuGroup.Builder()
<<<<<<< HEAD
                .id(dto.getId())
=======
>>>>>>> 0d1e94fb190f30830130e9e491a54a89f691ce7c
                .name(dto.getName())
                .build();
        menuGroupRepository.save(menuGroup);

        return new MenuGroupDto(menuGroup);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MenuGroupDto> list() {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MenuGroupDto> findMenuGroup(Long id) {
        return Optional.empty();
    }

    protected void validateRegisteredMenuGroup (final String name){
<<<<<<< HEAD
        if(menuGroupRepository.findByName(name)){
            throw new MenuGroupDuplicationException("동일한 메뉴그룹이 이미 등록 되어있습니다.");
=======
        if(menuGroupRepository.findByNameContaining(name)){
            throw new WrongNameException("동일한 메뉴그룹이 이미 등록 되어있습니다.");
>>>>>>> 0d1e94fb190f30830130e9e491a54a89f691ce7c
        }
    }

}
