package kitchenpos.menus.tobe.domain.menugroup.application;

import kitchenpos.menus.tobe.domain.menugroup.dto.MenuGroupDto;
import kitchenpos.menus.tobe.domain.menugroup.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.exception.MenuGroupDuplicationException;
import kitchenpos.menus.tobe.domain.menugroup.exception.MenuGroupNotFoundException;
import kitchenpos.menus.tobe.domain.menugroup.infra.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
                .id(dto.getId())
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
    public MenuGroupDto findMenuGroup(Long id) {
        MenuGroup savedMenuGroup = menuGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return new MenuGroupDto(savedMenuGroup);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isExist(Long id) {
        if(menuGroupRepository.findById(id).equals( Optional.empty() )){
            throw new MenuGroupNotFoundException("등록되지 않은 메뉴 그룹입니다.");
        }
        return true;
    }

    protected void validateRegisteredMenuGroup (final String name){
        if(menuGroupRepository.findByName(name)){
            throw new MenuGroupDuplicationException("동일한 메뉴그룹이 이미 등록 되어있습니다.");
        }
    }
}
