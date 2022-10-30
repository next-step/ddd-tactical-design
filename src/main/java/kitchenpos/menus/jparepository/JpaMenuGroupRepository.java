package kitchenpos.menus.jparepository;

import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends JpaRepository<MenuGroup, UUID>, MenuGroupRepository {
}
