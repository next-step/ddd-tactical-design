package kitchenpos.menus.tobe.menugroup.infra;

import kitchenpos.menus.tobe.menugroup.entity.MenuGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMenuGroupRepository implements MenuGroupRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public MenuGroup save(MenuGroup menuGroup) {
        em.persist(menuGroup);
        MenuGroup savedMenuGroup = findById(menuGroup.getId())
            .orElseThrow(() -> new RuntimeException("저장실패"));
        return savedMenuGroup;
    }

    @Override
    public Optional<MenuGroup> findById(Long id) {
        return Optional.ofNullable(em.find(MenuGroup.class, id));
    }

    @Override
    public List<MenuGroup> list() {
        return em.createQuery("select * from MenuGroup m")
            .getResultList();
    }

    @Override
    public boolean findByNameContaining(String name) {
        List<MenuGroup> menuGroups = em.createQuery("select * from MenuGroup m where m.name = :name")
            .setParameter("name", name)
            .getResultList();
        return menuGroups.isEmpty() ? true : false;
    }
}
