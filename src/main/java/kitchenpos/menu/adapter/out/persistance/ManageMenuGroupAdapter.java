package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;
import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.SaveMenuGroupPort;
import kitchenpos.menu.domain.exception.MenuGroupNameValidationException;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.ProfanityFilteringMenuGroupNameValidator;
import kitchenpos.shared.port.out.PurgomalumClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ManageMenuGroupAdapter implements LoadMenuGroupPort, SaveMenuGroupPort {
    private final MenuGroupEntityRepository menuGroupEntityRepository;
    private final PurgomalumClient purgomalumClient;

    public ManageMenuGroupAdapter(
            final MenuGroupEntityRepository menuGroupEntityRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.menuGroupEntityRepository = menuGroupEntityRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        return menuGroupEntityRepository.save(MenuGroupEntity.of(menuGroup))
                .toDomain(getProfanityFilteringMenuGroupNameValidator());
    }

    @Override
    public List<MenuGroup> findAll() {
        return menuGroupEntityRepository.findAll()
                .stream()
                .map(menuGroupEntity -> menuGroupEntity.toDomain(getProfanityFilteringMenuGroupNameValidator()))
                .toList();
    }

    @Override
    public Optional<MenuGroup> findById(UUID id) {
        return menuGroupEntityRepository.findById(id)
                .map(menuGroupEntity -> menuGroupEntity.toDomain(getProfanityFilteringMenuGroupNameValidator()));
    }

    private ProfanityFilteringMenuGroupNameValidator getProfanityFilteringMenuGroupNameValidator() {
        return name -> {
            if (purgomalumClient.containsProfanity(name)) {
                throw new MenuGroupNameValidationException("음식 그룹 이름에 비속어가 포함되어 있습니다. name=" + name);
            }
        };
    }
}
