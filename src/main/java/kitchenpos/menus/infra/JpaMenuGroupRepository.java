package kitchenpos.menus.infra;

import kitchenpos.menus.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<kitchenpos.menus.domain.MenuGroup, UUID> {
}
