package kitchenpos.menus.infra;

import java.util.UUID;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository,
        JpaRepository<MenuGroup, UUID> {

}
