package kitchenpos.menus.tobe.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Repository;

@Repository
class MenuAggregateRepository implements MenuRepository {
    private final JpaMenuRepository jpaMenuRepository;
    private final JpaMenuGroupRepository jpaMenuGroupRepository;

    public MenuAggregateRepository(JpaMenuRepository jpaMenuRepository, JpaMenuGroupRepository jpaMenuGroupRepository) {
        this.jpaMenuRepository = jpaMenuRepository;
        this.jpaMenuGroupRepository = jpaMenuGroupRepository;
    }

    @Override
    public Menu saveMenu(Menu menu) {
        return jpaMenuRepository.save(menu);
    }

    @Override
    public Optional<Menu> findMenuById(UUID id) {
        return jpaMenuRepository.findById(id);
    }

    @Override
    public List<Menu> findAllMenus() {
        return jpaMenuRepository.findAll();
    }

    @Override
    public List<Menu> findMenusByIdIn(List<UUID> ids) {
        return jpaMenuRepository.findAllById(ids);
    }

    @Override
    public List<Menu> findMenusByProductId(UUID productId) {
        return jpaMenuRepository.findMenusByProductId(productId);
    }

    @Override
    public MenuGroup saveMenuGroup(MenuGroup menuGroup) {
        return jpaMenuGroupRepository.save(menuGroup);
    }

    @Override
    public Optional<MenuGroup> findMenuGroupById(UUID id) {
        return jpaMenuGroupRepository.findById(id);
    }

    @Override
    public List<MenuGroup> findAllMenuGroups() {
        return jpaMenuGroupRepository.findAll();
    }
}
