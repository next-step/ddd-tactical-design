package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;
import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.SaveMenuGroupPort;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.shared.domain.Profanities;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ManageMenuGroupAdapter implements LoadMenuGroupPort, SaveMenuGroupPort {
    private final MenuGroupEntityRepository menuGroupEntityRepository;
    private final Profanities profanities;

    public ManageMenuGroupAdapter(
            final MenuGroupEntityRepository menuGroupEntityRepository,
            final Profanities profanities
    ) {
        this.menuGroupEntityRepository = menuGroupEntityRepository;
        this.profanities = profanities;
    }

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        return menuGroupEntityRepository.save(MenuGroupEntity.of(menuGroup))
                .toDomain(profanities);
    }

    @Override
    public List<MenuGroup> findAll() {
        return menuGroupEntityRepository.findAll()
                .stream()
                .map(menuGroupEntity -> menuGroupEntity.toDomain(profanities))
                .toList();
    }

    @Override
    public Optional<MenuGroup> findById(UUID id) {
        return menuGroupEntityRepository.findById(id)
                .map(menuGroupEntity -> menuGroupEntity.toDomain(profanities));
    }
}
