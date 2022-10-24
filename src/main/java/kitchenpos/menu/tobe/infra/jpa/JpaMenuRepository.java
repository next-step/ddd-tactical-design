package kitchenpos.menu.tobe.infra.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.repository.MenuRepository;
import kitchenpos.menu.tobe.infra.jpa.MenuEntityConverter.MenuEntityToMenuConverter;
import kitchenpos.menu.tobe.infra.jpa.MenuEntityConverter.MenuToMenuEntityConverter;
import kitchenpos.menu.tobe.infra.jpa.MenuProductEntityConverter.MenuProductToMenuProductEntityConverter;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMenuRepository implements MenuRepository {

    private final JpaMenuDao jpaMenuDao;

    private final JpaMenuGroupDao jpaMenuGroupDao;

    private final JpaMenuProductDao jpaMenuProductDao;

    private final MenuToMenuEntityConverter menuToMenuEntityConverter;

    private final MenuEntityToMenuConverter menuEntityToMenuConverter;

    private final MenuProductToMenuProductEntityConverter menuProductToMenuProductEntityConverter;

    public JpaMenuRepository(
        JpaMenuDao jpaMenuDao,
        JpaMenuGroupDao jpaMenuGroupDao,
        JpaMenuProductDao jpaMenuProductDao,
        MenuToMenuEntityConverter menuToMenuEntityConverter,
        MenuEntityToMenuConverter menuEntityToMenuConverter,
        MenuProductToMenuProductEntityConverter menuProductToMenuProductEntityConverter
    ) {
        this.jpaMenuDao = jpaMenuDao;
        this.jpaMenuGroupDao = jpaMenuGroupDao;
        this.jpaMenuProductDao = jpaMenuProductDao;
        this.menuToMenuEntityConverter = menuToMenuEntityConverter;
        this.menuEntityToMenuConverter = menuEntityToMenuConverter;
        this.menuProductToMenuProductEntityConverter = menuProductToMenuProductEntityConverter;
    }

    @Override
    public Menu save(final Menu menu) {
        final MenuEntity menuEntity = this.menuToMenuEntityConverter.convert(menu);
        final List<MenuProductEntity> menuProducts = this.menuProductToMenuProductEntityConverter.convert(
            menu
        );
        if (menuEntity.id != null) {
            this.jpaMenuProductDao.deleteByMenuId(menu.id);
        }
        return this.menuEntityToMenuConverter.convert(
            this.jpaMenuDao.save(menuEntity),
            menu.menuGroup,
            this.jpaMenuProductDao.saveAll(menuProducts)
        );
    }

    @Override
    public Optional<Menu> findById(final UUID id) {
        final MenuEntity menuEntity = this.jpaMenuDao.findById(id)
            .orElse(null);
        if (menuEntity == null) {
            return Optional.empty();
        }
        final List<MenuProductEntity> menuProductEntities = this.jpaMenuProductDao.findAllByMenuId(
            menuEntity.id
        );
        final MenuGroupEntity menuGroupEntity = this.jpaMenuGroupDao.findById(
                menuEntity.menuGroupId
            )
            .orElseThrow(() -> new IllegalStateException("MenuGroup을 찾을 수 없습니다."));
        return Optional.of(this.menuEntityToMenuConverter.convert(
            menuEntity,
            menuGroupEntity,
            menuProductEntities
        ));
    }

    @Override
    public List<Menu> findAll() {
        return this.jpaMenuDao.findAll()
            .stream()
            .map(menuEntity -> {
                final List<MenuProductEntity> menuProductEntities = this.jpaMenuProductDao.findAllByMenuId(
                    menuEntity.id
                );
                final MenuGroupEntity menuGroupEntity = this.jpaMenuGroupDao.findById(
                        menuEntity.menuGroupId
                    )
                    .orElseThrow(() -> new IllegalStateException("MenuGroup을 찾을 수 없습니다."));
                return this.menuEntityToMenuConverter.convert(
                    menuEntity,
                    menuGroupEntity,
                    menuProductEntities
                );
            })
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Menu> findAllByIdIn(final List<UUID> ids) {
        return this.jpaMenuDao.findAllByIdIn(ids)
            .stream()
            .map(menuEntity -> {
                final List<MenuProductEntity> menuProductEntities = this.jpaMenuProductDao.findAllByMenuId(
                    menuEntity.id
                );
                final MenuGroupEntity menuGroupEntity = this.jpaMenuGroupDao.findById(
                        menuEntity.menuGroupId
                    )
                    .orElseThrow(() -> new IllegalStateException("MenuGroup을 찾을 수 없습니다."));
                return this.menuEntityToMenuConverter.convert(
                    menuEntity,
                    menuGroupEntity,
                    menuProductEntities
                );
            })
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Menu> findAllByProductId(final UUID productId) {
        return this.jpaMenuDao.findAllByProductId(productId)
            .stream()
            .map(menuEntity -> {
                final List<MenuProductEntity> menuProductEntities = this.jpaMenuProductDao.findAllByMenuId(
                    menuEntity.id
                );
                final MenuGroupEntity menuGroupEntity = this.jpaMenuGroupDao.findById(
                        menuEntity.menuGroupId
                    )
                    .orElseThrow(() -> new IllegalStateException("MenuGroup을 찾을 수 없습니다."));
                return this.menuEntityToMenuConverter.convert(
                    menuEntity,
                    menuGroupEntity,
                    menuProductEntities
                );
            })
            .collect(Collectors.toUnmodifiableList());
    }
}
