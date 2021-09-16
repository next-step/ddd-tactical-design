package kitchenpos.menus.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaMenuGroupRepository extends TobeMenuGroupRepository, JpaRepository<TobeMenuGroup, UUID> {
}
