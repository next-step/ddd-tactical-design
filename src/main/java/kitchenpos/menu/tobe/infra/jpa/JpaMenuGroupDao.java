package kitchenpos.menu.tobe.infra.jpa;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMenuGroupDao extends JpaRepository<MenuGroupEntity, UUID> {
}
