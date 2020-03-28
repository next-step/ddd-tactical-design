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
    public MenuEntity save(MenuEntity menuEntity) {
        em.persist(menuEntity);
        return menuEntity;
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        return Optional.ofNullable(em.find(MenuEntity.class, id));
    }

    @Override
    public List<MenuEntity> findAll() {
        return em.createQuery("select * from MenuEntity m")
            .getResultList();
    }

    @Override
    public boolean findByName(String name) {
        List<MenuEntity> result = em.createQuery("select * from MenuEntity m where m.name = :name")
            .setParameter("name", name)
            .getResultList();

        return result.size() > 0 ? true : false;
    }
}
