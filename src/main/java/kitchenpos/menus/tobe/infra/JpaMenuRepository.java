package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuRepository extends JpaRepository<Menu, UUID>, MenuRepository {
}
