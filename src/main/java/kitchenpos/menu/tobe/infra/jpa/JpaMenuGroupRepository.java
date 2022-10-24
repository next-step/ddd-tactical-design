package kitchenpos.menu.tobe.infra.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menu.tobe.infra.jpa.MenuGroupEntityConverter.MenuGroupEntityToMenuGroupConverter;
import kitchenpos.menu.tobe.infra.jpa.MenuGroupEntityConverter.MenuGroupToMenuGroupEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMenuGroupRepository implements MenuGroupRepository {

    private final JpaMenuGroupDao jpaMenuGroupDao;

    private final MenuGroupToMenuGroupEntityConverter menuGroupToMenuGroupEntityConverter;

    private final MenuGroupEntityToMenuGroupConverter menuGroupEntityToMenuGroupConverter;

    @Autowired
    public JpaMenuGroupRepository(
        final JpaMenuGroupDao jpaMenuGroupDao,
        final MenuGroupToMenuGroupEntityConverter menuGroupToMenuGroupEntityConverter,
        final MenuGroupEntityToMenuGroupConverter menuGroupEntityToMenuGroupConverter
    ) {
        this.jpaMenuGroupDao = jpaMenuGroupDao;
        this.menuGroupToMenuGroupEntityConverter = menuGroupToMenuGroupEntityConverter;
        this.menuGroupEntityToMenuGroupConverter = menuGroupEntityToMenuGroupConverter;
    }

    @Override
    public MenuGroup save(final MenuGroup menuGroup) {
        final MenuGroupEntity menuGroupEntity = this.menuGroupToMenuGroupEntityConverter.convert(menuGroup);
        final MenuGroupEntity result = this.jpaMenuGroupDao.save(menuGroupEntity);
        return this.menuGroupEntityToMenuGroupConverter.convert(result);
    }

    @Override
    public Optional<MenuGroup> findById(final UUID id) {
        final Optional<MenuGroupEntity> result = this.jpaMenuGroupDao.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.menuGroupEntityToMenuGroupConverter.convert(result.get()));
    }

    @Override
    public List<MenuGroup> findAll() {
        return this.jpaMenuGroupDao.findAll()
            .stream()
            .map(this.menuGroupEntityToMenuGroupConverter::convert)
            .collect(Collectors.toUnmodifiableList());
    }
}
