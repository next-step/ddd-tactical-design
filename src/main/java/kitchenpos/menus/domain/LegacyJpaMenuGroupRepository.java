package kitchenpos.menus.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LegacyJpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
