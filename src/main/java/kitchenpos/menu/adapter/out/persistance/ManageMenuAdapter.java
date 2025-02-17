package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import kitchenpos.menu.application.port.out.LoadMenuPort;
import kitchenpos.menu.application.port.out.SaveMenuPort;
import kitchenpos.menu.domain.model.Menu;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ManageMenuAdapter implements LoadMenuPort, SaveMenuPort {
    private final MenuEntityRepository menuRepository;

    public ManageMenuAdapter(MenuEntityRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return menuRepository.findById(id)
                .map(MenuEntity::toDomain);
    }

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(MenuEntity.of(menu))
                .toDomain();
    }
}
