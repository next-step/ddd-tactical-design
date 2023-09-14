package kitchenpos.menus.domain.tobe.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToBeJpaMenuGroupRepository extends ToBeMenuGroupRepository, JpaRepository<ToBeMenuGroup, UUID> {
}
