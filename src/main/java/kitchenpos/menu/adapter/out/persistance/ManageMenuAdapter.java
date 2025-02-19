package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import kitchenpos.menu.application.port.out.LoadMenuPort;
import kitchenpos.menu.application.port.out.SaveMenuPort;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.shared.domain.Profanities;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ManageMenuAdapter implements LoadMenuPort, SaveMenuPort {
    private final JpaMenuEntityEntityRepository menuRepository;
    private final Profanities profanities;

    public ManageMenuAdapter(
            final JpaMenuEntityEntityRepository menuRepository,
            final Profanities profanities
    ) {
        this.menuRepository = menuRepository;
        this.profanities = profanities;
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(menuEntity -> menuEntity.toDomain(profanities))
                .toList();
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return menuRepository.findById(id)
                .map(menuEntity -> menuEntity.toDomain(profanities));
    }

    @Override
    public List<Menu> findByProductId(UUID productId) {
        return menuRepository.findAllByProductId(productId)
                .stream()
                .map(menuEntity -> menuEntity.toDomain(profanities))
                .toList();
    }

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(MenuEntity.of(menu))
                .toDomain(profanities);
    }

    @Override
    public void saveAll(List<Menu> menu) {
        List<MenuEntity> list = menu.stream()
                .map(MenuEntity::of)
                .toList();
        menuRepository.saveAll(list);
    }
}
