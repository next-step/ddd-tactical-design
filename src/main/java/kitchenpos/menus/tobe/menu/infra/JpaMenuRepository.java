package kitchenpos.menus.tobe.menu.infra;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaMenuRepository {

    @PersistenceContext
    private EntityManager em;

}
