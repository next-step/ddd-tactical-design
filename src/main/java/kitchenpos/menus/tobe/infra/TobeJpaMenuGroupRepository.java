package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaMenuGroupRepository extends TobeMenuGroupRepository, JpaRepository<TobeMenuGroup, UUID> {
}
