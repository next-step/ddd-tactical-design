package kitchenpos.menugroups.infra;

import java.util.UUID;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository,
        JpaRepository<MenuGroup, UUID> {

}
