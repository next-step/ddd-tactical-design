package kitchenpos.apply.menus.tobe.infra;

import kitchenpos.apply.menus.tobe.domain.Menu;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {
}
