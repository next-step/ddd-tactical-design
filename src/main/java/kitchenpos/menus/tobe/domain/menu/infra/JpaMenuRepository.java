package kitchenpos.menus.tobe.domain.menu.infra;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMenuRepository implements MenuRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MenuEntity register(MenuEntity menuEntity) {
        return null;
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<MenuEntity> findAll() {
        return null;
    }

    @Override
    public long countByIdIn(List<Long> ids) {
        return 0;
    }
}
