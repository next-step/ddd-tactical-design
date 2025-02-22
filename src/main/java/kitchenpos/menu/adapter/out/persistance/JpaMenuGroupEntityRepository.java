package kitchenpos.menu.adapter.out.persistance;

import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupEntityRepository extends JpaRepository<MenuGroupEntity, UUID> {
}
