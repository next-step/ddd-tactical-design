package kitchenpos.domain.menu.tobe.infra;

import kitchenpos.domain.menu.tobe.domain.MenuGroup;
import kitchenpos.domain.menu.tobe.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
