package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {
}
