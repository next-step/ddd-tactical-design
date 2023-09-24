package kitchenpos.menus.infra;


import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.NewMenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewJpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<NewMenuGroup, UUID> {
}
