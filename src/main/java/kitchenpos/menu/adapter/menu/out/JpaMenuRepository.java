package kitchenpos.menu.adapter.menu.out;

import java.util.UUID;
import kitchenpos.menu.application.menu.port.out.MenuRepository;
import kitchenpos.menu.domain.menu.MenuNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<MenuNew, UUID> {

}
