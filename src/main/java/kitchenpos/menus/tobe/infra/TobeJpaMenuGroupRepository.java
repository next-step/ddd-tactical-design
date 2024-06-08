package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaMenuGroupRepository extends TobeMenuGroupRepository, JpaRepository<TobeMenuGroup, UUID> {
}
