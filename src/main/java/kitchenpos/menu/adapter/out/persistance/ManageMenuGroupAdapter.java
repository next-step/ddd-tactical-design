package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;
import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.SaveMenuGroupPort;
import kitchenpos.menu.domain.model.MenuGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageMenuGroupAdapter implements LoadMenuGroupPort, SaveMenuGroupPort {
    private final MenuGroupEntityRepository menuGroupEntityRepository;

    public ManageMenuGroupAdapter(final MenuGroupEntityRepository menuGroupEntityRepository) {
        this.menuGroupEntityRepository = menuGroupEntityRepository;
    }

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        return menuGroupEntityRepository.save(MenuGroupEntity.of(menuGroup))
                .toDomain();
    }

    @Override
    public List<MenuGroup> findAll() {
        return menuGroupEntityRepository.findAll()
                .stream()
                .map(MenuGroupEntity::toDomain)
                .toList();
    }
}
