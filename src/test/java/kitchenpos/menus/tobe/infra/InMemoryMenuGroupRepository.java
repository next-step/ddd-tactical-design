package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.menugroup.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.infra.MenuGroupRepository;

import java.util.*;

public class InMemoryMenuGroupRepository implements MenuGroupRepository {

    private Map<Long, MenuGroup> entities = new HashMap<>();

    @Override
    public MenuGroup save(MenuGroup menuGroup) {
        MenuGroup savedMenuGroup = new MenuGroup.Builder()
                .id(menuGroup.getId())
                .name(menuGroup.getName())
                .build();

        entities.put(savedMenuGroup.getId(), savedMenuGroup);

        return savedMenuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<MenuGroup> list() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public boolean findByName(String name) {
        List<MenuGroup> menuGroups = this.list();

        for(MenuGroup menuGroup : menuGroups){
            if(name.equals(menuGroup.getName())){
                return true;
            }
        }
        return false;
    }
}
