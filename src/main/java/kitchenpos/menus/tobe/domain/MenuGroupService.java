package kitchenpos.menus.tobe.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuGroupService {

    private MenuGroupRepository menuGroupRepository;

    public MenuGroupService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroup menuGroup) {
        return menuGroupRepository.save(menuGroup);
    }

    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }

    public void existsMenuGroupById(Long groupId) {
        menuGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴 그룹이 존재하지 않습니다."));
    }
}
