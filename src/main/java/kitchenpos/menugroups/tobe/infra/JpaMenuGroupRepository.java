package kitchenpos.menugroups.tobe.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.menugroups.tobe.domain.MenuGroup;
import kitchenpos.menugroups.tobe.domain.MenuGroupRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
