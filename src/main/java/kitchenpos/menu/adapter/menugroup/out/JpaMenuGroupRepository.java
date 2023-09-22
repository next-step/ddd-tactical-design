package kitchenpos.menu.adapter.menugroup.out;

import java.util.UUID;
import kitchenpos.menu.application.menugroup.port.out.MenuGroupRepository;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository,
    JpaRepository<MenuGroupNew, UUID> {

}
